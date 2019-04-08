package com.fdm.actions;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.util.MessageResources;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.actions.DispatchAction;
import com.fdm.db.BasketAccess;
import com.fdm.db.FilePath;
import com.fdm.db.PersonAccess;
import com.fdm.db.PurchaseAccess;
import com.fdm.forms.PurchaseHistoryForm;
import com.fdm.shopping.Basket;
import com.fdm.shopping.PaymentInfo;
import com.fdm.shopping.Person;
import com.fdm.shopping.State;
import com.fdm.tools.InputChecker;
import com.fdm.tools.Logging;
import com.fdm.forms.CheckoutForm;


public class CheckoutAction extends DispatchAction
{
	private boolean submit;
	private PersonAccess pers_access;
	private BasketAccess bas_access;
	private PurchaseAccess purchase_access;
	private PaymentInfo paymentInfo;
	private List<Basket> checkedBasketList;
	private String logoutButton;
	private String viewOrderButton;
	private String selectCardButton;
	private String cancelButton;
	private String buySelectedCardButton;
	private String purchaseButton;
	private String removeCardButton;
	private String pay_method;
	private MessageResources messageResources;
	private FilePath propFilePath;
	private HttpSession session;
	private State state;
	private CheckoutForm checkoutForm;
	private String buttonPressed;
	
	
	
	
	public ActionForward execute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception 
    {
		init(request,form);
		Logging.setLog(CheckoutAction.class,state);
		Logging.getLog().debug("CheckoutAction.......");
		String direction="";
		if (logoutButton.equals(buttonPressed))
		{
			direction = logoutRequested();
		}
		else if (viewOrderButton.equals(buttonPressed))
		{
			direction = viewOrderRequested();
		}
		else if (cancelButton.equals(buttonPressed))
		{
			direction = cancelButtonPressed(state,checkoutForm);
		}
		else if (purchaseButton.equals(buttonPressed))
		{
			direction = purchaseButtonPressed(request,response,state,checkoutForm);
		}
		else if (buySelectedCardButton.equals(buttonPressed))  
		{
			direction = purchaseWithThisCardButtonPressed(request,response,state,checkoutForm);
		}
		else if (removeCardButton.equals(buttonPressed))
		{
			direction = removeCardButtonPressed(request,response,state,checkoutForm,pay_method);
		}
		Logging.getLog().debug("pay_method = " + pay_method);
		if (pay_method != null && direction.equals("")) // then radio button is active
		{
			direction = payMehodRadioChecked(state,pay_method);
		}
		Logging.getLog().debug("direction = " + direction);
		return mapping.findForward(direction);
    }
	
	
	
	private void init(HttpServletRequest request,ActionForm form)
	{
		session = request.getSession();
		state = (State)session.getAttribute("state");
		state.clearMessages();
		messageResources = getResources(request);
		propFilePath = new FilePath(messageResources);
		checkoutForm = (CheckoutForm)form;
		initButtons(request);
		initDB(propFilePath);
	}
	
	
	
	
	private void initButtons(HttpServletRequest request)
	{
		logoutButton = messageResources.getMessage("checkout.logout_button");
		viewOrderButton = messageResources.getMessage("checkout.view_order_button");
		selectCardButton = messageResources.getMessage("checkout.select_card_button");
		cancelButton = messageResources.getMessage("checkout.cancel_button").trim();
		buySelectedCardButton = messageResources.getMessage("checkout.purchase_selected_card_button");
		purchaseButton = messageResources.getMessage("checkout.purchase_button");
		removeCardButton = messageResources.getMessage("checkout.remove_card_button").trim();
		pay_method = request.getParameter("pay_method");
		buttonPressed = checkoutForm.getSubmit().trim();
		Logging.getLog().debug("buttonPressed = " + buttonPressed);
	}
	
	
	
	
	
	private void initDB(FilePath propFilePath)
	{
		pers_access = new PersonAccess(propFilePath);
		bas_access = new BasketAccess(propFilePath);
		purchase_access = new PurchaseAccess(propFilePath);
		paymentInfo = null;
	}
	
	
	
	

	
	private String logoutRequested()
	{
		session.invalidate();
		Logging.getLog().debug("Log out button pressed");
		return "login";
	}
	
	
	
	private String viewOrderRequested()
	{
		state.setViewOrder("ON");
		Logging.getLog().debug("View Order button pressed");
		return "checkout";
	}
	
	
	
	
	
	

	public boolean isSubmit() {
		return submit;
	}


	public void setSubmit(boolean submit) {
		this.submit = submit;
	}
	
	
	private String cancelButtonPressed(State state,CheckoutForm checkoutForm)
	{
		state.clearPaymentRecords();
		state.setViewOrder("OFF");
		checkoutForm.refreshCardDetails();
		return "query_page";
	}
	
	
	
	
	public String removeCardButtonPressed(HttpServletRequest request, HttpServletResponse response,
            State state,CheckoutForm checkoutForm,String payMethod) throws IOException, ServletException
    {
		Logging.getLog().debug("remove button pressed");
		state.setChosenPayment(null);
		int index = (Integer.parseInt(payMethod)) - 1;
		int userId = state.getUser().getId();
		Person statePerson = state.getUser().getPerson();
		List<PaymentInfo> payInfoList = statePerson.getPaymentInfoList();
		PaymentInfo payInfo = payInfoList.get(index);
		String cardName = payInfo.getNameOnCard();
		String cardNumber = payInfo.getCardNumber();
		String secCode = payInfo.getSecurityCode();
		boolean deactivated = bas_access.deactivatePaymentMethod(cardName,cardNumber,secCode);
		if (deactivated)
		{
			state.getUser().getPerson().setPaymentInfoList(bas_access.getPaymentInfoList(userId));
		}
		return "checkout";
	}

	
	
	
	private String payMehodRadioChecked(State state,String pay_method)
	{
		Logging.getLog().debug("direction is blank string, and pay_method != null");
		PaymentInfo chosenPayment = getChosenPayment(pay_method,state);
		state.setChosenPayment(chosenPayment);
		state.setSelectPaymentStatus("ON");
		return "checkout";
	}
	
	
	
	
	
	private String purchaseButtonPressed(HttpServletRequest request, HttpServletResponse response,
			                             State state,CheckoutForm checkoutForm)
                                            throws IOException, ServletException
    {
		paymentInfo = getPaymentInfo(request,response,state,checkoutForm);
		if (paymentInfo == null)
		{
			return "checkout";
		}
		state.clearPaymentRecords();
		state.setViewOrder("OFF");
		checkoutForm.refreshCardDetails(); 
		Logging.getLog().debug("purchase button pressed");
		return processPurchase(request,response,state,checkoutForm);
    }

	
	
	
	
	private String purchaseWithThisCardButtonPressed(HttpServletRequest request, HttpServletResponse response,
			                                         State state,CheckoutForm checkoutForm) throws IOException, ServletException
	{
		paymentInfo = state.getChosenPayment();
		state.clearPaymentRecords();
		state.setViewOrder("OFF");
		checkoutForm.refreshCardDetails(); 
		return processPurchase(request,response,state,checkoutForm);
	}
	
	
	
	
	
	private PaymentInfo getChosenPayment(String pay_method, State state)
	{
		int index = (Integer.parseInt(pay_method)) - 1;
		List<PaymentInfo> payInfoList = state.getUser().getPerson().getPaymentInfoList();
		return payInfoList.get(index);
	}
	
	
	
	
	
	private void updateBasketList(List<Basket> basketList,String cardNumber,String nameOnCard,String cardType,
			                      String securityCode,Date expiryDate,Date validFrom)
	{
		for (int i = 0; i < basketList.size(); i++)
		{
			Basket basket = basketList.get(i);
			basket.setCardType(cardType);
			basket.setCardNumber(cardNumber);
			basket.setNameOnCard(nameOnCard);
			basket.setSecurityCode(securityCode);
			basket.setExpiryDate(expiryDate);
			basket.setValidFrom(validFrom);
		}
	}
	
	
	
	
	
	private String processPurchase(HttpServletRequest request, HttpServletResponse response,State state,
			                       CheckoutForm checkoutForm) throws IOException, ServletException
	{
		if (state.getBasketList().isEmpty())
		{
			state.setMessage("Your basket is empty! Please add routes to basket for purchasing.");
			return "checkout";
		}
		if (validatePayment(paymentInfo))
		{
			checkedBasketList = state.getCheckedBasketList();
			String cardNumber = paymentInfo.getCardNumber().substring(0,15);
			String nameOnCard = paymentInfo.getNameOnCard();
			String cardType = paymentInfo.getCardType();
			String securityCode = paymentInfo.getSecurityCode();
			Date expiryDate = paymentInfo.getExpiry_date();
			Date validFrom = paymentInfo.getValid_from();
			updateBasketList(checkedBasketList,cardNumber,nameOnCard,cardType,securityCode,expiryDate,validFrom);
			boolean basketsUpdated = bas_access.updatePurchasedBaskets(checkedBasketList);
			checkoutForm.refreshCardDetails();
			return postPurchaseAttempt(true,state);
		}
		else
		{
			state.setMessage("Card details could not be validated. Please try again with valid payment details.");
			checkoutForm.refreshCardDetails();
			return "basket";
		}
	}
	
	
	
	
	
	
	
	
	
	private boolean recordPaymentDetails(State state,PaymentInfo paymentInfo)
	{
		if (! detailsAlreadyExist(paymentInfo))
		{
			return addPaymentInfo(paymentInfo);
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	private boolean addPaymentInfo(PaymentInfo paymentInfo)
	{
		List<PaymentInfo> paymentList = new LinkedList<PaymentInfo>();
		paymentList.add(paymentInfo);
		return pers_access.registerPaymentDetails(paymentList);
	}
	
	
	
	
	
	
	private boolean detailsAlreadyExist(PaymentInfo paymentInfo)
	{
		return pers_access.paymentInfoExists(paymentInfo);
	}
	
	
	
	
	private String postPurchaseAttempt(boolean basketsUpdated,State state)
	{
		if (basketsUpdated)
		{
			List<Basket> basketList = getBasketDBList(state);
			state.setBasketList(basketList);
			List<Basket> purchasedBasketList = getPurchasedBasketDBList(state);
			state.setPurchasedBasketList(purchasedBasketList);
			state.setStatus("POST_SALE_CONFIRMATION");
			return "sale_confirmation";
		}
		return "basket";
	}
	
	
	
	private List<Basket> getPurchasedBasketDBList(State state)
	{
		List<Basket> basketList = null;
		basketList = bas_access.getPurchasedBasketList(state.getUser().getId());
		if (basketList == null)
		{
			return new LinkedList<Basket>();
		}
		return basketList;
	}
	
	
	
	
	private List<Basket> getBasketDBList(State state)
	{
		List<Basket> basketList = null;
		try
		{
			basketList = bas_access.getUnpurchasedBasketList(state.getUser().getId());
		}
		catch(SQLException e)
		{
			return new LinkedList<Basket>();
		}
		return basketList;
	}
	
		
	
	
	
		
	private boolean validatePayment(PaymentInfo paymentInfo)
	{
		
		return true;
	}
	
	
	
	
	private PaymentInfo getPaymentInfo(HttpServletRequest request, HttpServletResponse response,
			                           State state,CheckoutForm checkoutForm)
	                                              throws IOException, ServletException
	{
		String nameOnCard = checkoutForm.getName_on_card();
		String paymentMethod = checkoutForm.getPayment_method();
		String cardNumber = checkoutForm.getCard_number();
		String securityCode = checkoutForm.getSecurity_code();
		int expDay = Integer.parseInt(checkoutForm.getExpiry_date());
		int expMonth = Integer.parseInt(checkoutForm.getExpiry_month());
		int expYear = (Integer.parseInt(checkoutForm.getExpiry_year()) - 1900);
		int valDay = Integer.parseInt(checkoutForm.getValid_from_date());
		int valMonth = Integer.parseInt(checkoutForm.getValid_from_month());
		int valYear = (Integer.parseInt(checkoutForm.getValid_from_year()) - 1900);
		Date validityDate = new Date(valYear,valMonth,valDay,0,0);
		Date expiryDate = new Date(expYear,expMonth,expDay,0,0);
		InputChecker checker = new InputChecker();
		boolean dateFieldsPlausible = checker.areCardDatesPlausible(validityDate,expiryDate,state,
                                                         expDay,expMonth,expYear,valDay,valMonth,valYear);
		boolean dateFieldsOK = checker.cardDatesValidNow(validityDate,expiryDate,state);
		boolean nonDateFieldsOK = checker.areNonDateFieldsValid(nameOnCard,paymentMethod,cardNumber,securityCode,state);
		if (dateFieldsOK == true && nonDateFieldsOK == true && dateFieldsPlausible == true)
		{
			PaymentInfo paymentInfo = new PaymentInfo(paymentMethod,cardNumber,securityCode,expiryDate,validityDate,null,nameOnCard);
			paymentInfo.setId(state.getUser().getPerson().getId());
			return paymentInfo;
		}
		return null;
	}

	
	
	
	
	
	
	
}
