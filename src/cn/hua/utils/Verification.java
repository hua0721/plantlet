package cn.hua.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.hua.action.AddressAction;
import cn.hua.action.OrderFormAction;
import cn.hua.action.RegisterAction;
import cn.hua.action.UserMoreInfoModify;
import cn.hua.formBean.GoodsForm;
import cn.hua.formBean.UserLogin;
import cn.hua.formBean.UserRegister;
import cn.hua.model.OrderForm;
import cn.hua.model.Safe;
import cn.hua.model.Takedelivery;
import cn.hua.model.User;
import cn.hua.service.Service;

@Component
public class Verification {
	private static Service service;

	@Resource
	public void setService(Service service) {
		Verification.service = service;
	}
	/*
	 * 验证表单，后续通过struts2字段验证修改
	 */
	public static boolean register(RegisterAction registerAction,
			UserRegister userRegister) {
		if (userRegister != null) {
			Pattern pattern;
			Matcher matcher;
			int count = 0;
			if ("phone".equals(userRegister.getRegisterType())) {
				if (userRegister.getPhone() == null
						|| userRegister.getPhone().trim().equals("")) {
					registerAction.addFieldError("phoneError",
							registerAction.getText("phoneNull"));
					count++;
				} else {
					pattern = Pattern.compile("^[1][3-8][0-9]{9}$");
					matcher = pattern.matcher(userRegister.getPhone().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("phoneError",
								registerAction.getText("phoneFormatError"));
						count++;
					} else {
						if (service.UserIsExist("phone",
								userRegister.getPhone())) {
							registerAction.addFieldError("phoneError",
									registerAction.getText("phoneInvalid"));
							count++;
						}
					}
				}
				if (userRegister.getEmail() != null
						&& !userRegister.getEmail().trim().equals("")) {
					pattern = Pattern
							.compile("^[0-9a-zA-Z]+@[0-9a-zA-Z]+[.][0-9a-zA-Z]+$");
					matcher = pattern.matcher(userRegister.getEmail().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("emailError",
								registerAction.getText("emailFormatError"));
						count++;
					} else {
						if (service.UserIsExist("email",
								userRegister.getEmail())) {
							registerAction.addFieldError("emailError",
									registerAction.getText("emailInvalid"));
							count++;
						}
					}
				}

			} else if ("username".equals(userRegister.getRegisterType())) {
				if (userRegister.getUsername() == null
						|| userRegister.getUsername().trim().equals("")) {
					registerAction.addFieldError("usernameError",
							registerAction.getText("usernameNull"));
					count++;
				} else {
					pattern = Pattern.compile("^[a-zA-Z]+[0-9a-zA-Z]{4,20}$");
					matcher = pattern
							.matcher(userRegister.getUsername().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("usernameError",
								registerAction.getText("usernameFormatError"));
						count++;
					} else {
						if (service.UserIsExist("username",
								userRegister.getUsername())) {
							registerAction.addFieldError("usernameError",
									registerAction.getText("usernameInvalid"));
							count++;
						}
					}
				}
				if (userRegister.getPhone() != null
						&& !userRegister.getPhone().trim().equals("")) {
					System.out.println(">" + userRegister.getPhone() + "<");
					pattern = Pattern
							.compile("^[1][3-8][0-9]{9}$");
					matcher = pattern.matcher(userRegister.getPhone().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("phoneError",
								registerAction.getText("phoneFormatError"));
						count++;
					} else {
						if (service.UserIsExist("phone",
								userRegister.getPhone())) {
							registerAction.addFieldError("phoneError",
									registerAction.getText("phoneInvalid"));
							count++;
						}
					}
				}
				if (userRegister.getEmail() != null
						&& !userRegister.getEmail().trim().equals("")) {
					pattern = Pattern
							.compile("^[0-9a-zA-Z]+@[0-9a-zA-Z]+[.][0-9a-zA-Z]+$");
					matcher = pattern.matcher(userRegister.getEmail().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("emailError",
								registerAction.getText("emailFormatError"));
						count++;
					} else {
						if (service.UserIsExist("email",
								userRegister.getEmail())) {
							registerAction.addFieldError("emailError",
									registerAction.getText("emailInvalid"));
							count++;
						}
					}
				}

			} else if ("email".equals(userRegister.getRegisterType())) {
				if (userRegister.getEmail() == null
						|| userRegister.getEmail().trim().equals("")) {
					registerAction.addFieldError("emailError",
							registerAction.getText("emailNull"));
					count++;
				} else {
					pattern = Pattern
							.compile("^[0-9a-zA-Z]+@[0-9a-zA-Z]+[.][0-9a-zA-Z]+$");
					matcher = pattern.matcher(userRegister.getEmail().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("emailError",
								registerAction.getText("emailFormatError"));
						count++;
					} else {
						if (service.UserIsExist("email",
								userRegister.getEmail())) {
							registerAction.addFieldError("emailError",
									registerAction.getText("emailInvalid"));
							count++;
						}
					}
				}
				if (userRegister.getPhone() != null
						&& !userRegister.getPhone().trim().equals("")) {
					pattern = Pattern
							.compile("^[1][3-8][0-9]{9}$");
					matcher = pattern.matcher(userRegister.getPhone().trim());
					if (!matcher.find()) {
						registerAction.addFieldError("phoneError",
								registerAction.getText("phoneFormatError"));
						count++;
					} else {
						if (service.UserIsExist("phone",
								userRegister.getPhone())) {
							registerAction.addFieldError("phoneError",
									registerAction.getText("phoneInvalid"));
							count++;
						}
					}
				}
			}
			count = count
					+ matchPassword(registerAction, userRegister.getPassword(),
							userRegister.getPassword2());
			if (count > 0) {
				return false;
			}
		}
		return true;
	}

	public static int matchPassword(RegisterAction registerAction,
			String password, String password2) {
		int count = 0;
		if (password == null || password.trim().equals("")) {
			registerAction.addFieldError("passwordError",
					registerAction.getText("passwordNull"));
			count++;
		}
		if (password2 == null || password2.trim().equals("")) {
			registerAction.addFieldError("password2Error",
					registerAction.getText("passwordNull"));
			count++;
		}
		if (count > 0) {
			if (!password.trim().equals(password2.trim())) {
				registerAction.addFieldError("password2Error",
						registerAction.getText("twicePasswordSame"));
				count++;
			}
		}
		return count;
	}
	/**
	 * 该方法会通过登陆Bean向数据库检查用户是否合法
	 * @param userLogin
	 * @return 检查通过返回当前用户,反之返回NULL
	 */
	public static User loginVerification(UserLogin userLogin) {
		if (userLogin != null && userLogin.getAccount() != null
				&& !userLogin.getAccount().trim().equals("")
				&& userLogin.getPassword() != null&&!userLogin.getPassword().trim().equals("")) {
			User user = service.findAccount(userLogin.getAccount());
			if(user!=null){
				Safe safe = user.getSafe();
				//密码比对
				if(safe.getLoginPassword().equals(Encryption.encryption(userLogin.getPassword()+safe.getId()))){
					return user;
				}
			}
		}
		return null;
	}
	public static String[] goodsVer(GoodsForm goods) {
		String[] error = new String[1];
		int i=0;
		if(goods==null){
			error[i++]="发生错误";
			return error;
		}
		if(goods.getGoodsKindTemp()==0){
			error[i++] = "商品分类必须还未选择";
			return error;
		}
		/*if(goods.getMoreKindTemp()==null){
			error[i++] = "商品详细分类至少选择一项";
			return error;
		}*/
		if(goods.getName()==null||goods.getName()!=null&&goods.getName().trim().equals("")){
			error[i++] = "商品名称不能为空";
			return error;
		}
		if(goods.getSimpleDescript()==null||goods.getSimpleDescript()!=null&&goods.getSimpleDescript().trim().equals("")){
			error[i++] = "商品简述不能为空";
			return error;
		}
		if(goods.getPrice()==0){
			error[i++] = "商品价格不能为0";
			return error;
		}
		if(goods.getIsSale()==1){
			if(goods.getSalePrice()==0){
				error[i++] = "商品打折价格不能为0";
				return error;
			}
			if(goods.getSaleTime()==null){
				error[i++] = "商品打折日期不能为空";
				return error;
			}
		}
		return null;
	}
	
	public static String[] orderForm(OrderFormAction action, OrderForm orderForm) {
		String[] error = new String[1];
		int i=0;
		if(orderForm==null){
			error[i++]=action.getText("happenError");
			return error;
		}
		if(orderForm.getGoods()==null){
			error[i++] = action.getText("noGoodsInfor");
			return error;
		}
		if(orderForm.getBuyNum()<1){
			error[i++] = action.getText("goodsNumGt1");
			return error;
		}
		return null;
	}
	public static String[] address(AddressAction action, Takedelivery take){
		String[] error = new String[1];
		int i=0;
		if(take==null){
			error[i++]=action.getText("happenError");
			return error;
		}
		if(take.getAddress()==null){
			error[i++]=action.getText("addressNull");
			return error;
		}
		if(take.getMoreAddress()==null){
			error[i++]=action.getText("detailedAddNull");
			return error;
		}
		if(take.getPhone()==null&&take.getTelephone()==null){
			error[i++]=action.getText("numMustFill");
			return error;
		}
		System.out.println(take.getPhone());
		if(take.getPhone()!=null&&!take.getPhone().trim().matches("^[1][3-8][0-9]{9}$")){
			error[i++]=action.getText("phoneFormatError");
			return error;
		}
		if(take.getTelephone()!=null&&!take.getTelephone().trim().matches("^[0-9]{0,22}$")){
			error[i++]=action.getText("telePhoneFormatError");
			return error;
		}
		return null;
		}
	public static String[] myCenterSafe(UserMoreInfoModify action, String type,String oldData,String oldData2,String newData,User user){
		String[] error;
		boolean bool=true;
		int i=0;
		if("phone".equals(type)){
			error = new String[2];
			if(user.getPhone()!=null&&!user.getPhone().equals(oldData)){
				error[i++]="oldPhone,"+action.getText("oldPhoneReject");
				bool=false;
			}
			if(newData==null||newData!=null&&!newData.matches("^[1][3-8][0-9]{9}$")){
				error[i++]="newPhone,"+action.getText("phoneErrorReject");
				bool=false;
			}
		}else if("email".equals(type)){
			error = new String[2];
			if(user.getEmail()!=null&&!user.getEmail().equals(oldData)){
				error[i++]="oldEmail,"+action.getText("oldEmailReject");
				bool=false;
			}
			if(newData==null||newData!=null&&!newData.matches("^[0-9a-zA-Z]+@[0-9a-zA-Z]+[.][0-9a-zA-Z]+$")){
				error[i++]="newEmail,"+action.getText("emailErrorReject");
				bool=false;
			}
		}else if("loginPassword".equals(type)){
			error = new String[2];
			System.out.println(Encryption.encryption(oldData+user.getId()));
			if(!user.getSafe().getLoginPassword().equals(Encryption.encryption(oldData+user.getSafe().getId()))){
				error[i++]="oldLoginPassword,"+action.getText("oldLPasswordReject");
				bool=false;
			}
			if(newData==null||newData!=null&&(newData.length()<5||newData.length()>20)){
				error[i++]="newEmail,"+action.getText("passwordLengthReject");
				bool=false;
			}
		}else{
			error = new String[3];
			if(!user.getSafe().getLoginPassword().equals(Encryption.encryption(oldData+user.getSafe().getId()))){
				error[i++]="oldLoginPassword,"+action.getText("oldLPasswordReject");
				bool=false;
			}
			if(user.getSafe().getPayPassword()!=null&&!user.getSafe().getPayPassword().equals(Encryption.encryption(oldData2+user.getSafe().getId()))){
				error[i++]="oldPayPassword,"+action.getText("oldPPasswordReject");
				bool=false;
			}
			if(newData==null||newData!=null&&(newData.length()<5||newData.length()>20)){
				error[i++]="newEmail,"+action.getText("passwordLengthReject");
				bool=false;
			}
		}
		if(bool)return null;
		else return error;
	}
	public static String[] myCenterInfo(UserMoreInfoModify action, User user){
		String[] error = new String[1];
		if(user.getUsername()!=null&&!user.getUsername().matches("^[a-zA-Z]+[0-9a-zA-Z]{4,20}$")){
			error[0] = action.getText("usernameFormatError");
			return error;
		}
		if(user.getIdentity()!=null&&user.getIdentity().getIdentityNumber()!=null&&!user.getIdentity().getIdentityNumber().equals("")&&!user.getIdentity().getIdentityNumber().matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$")){
			error[0] = action.getText("idFormatError");
			return error;
		}
		return null;
	}
}
