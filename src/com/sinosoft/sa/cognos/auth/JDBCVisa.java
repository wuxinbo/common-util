package com.sinosoft.sa.cognos.auth;

/**
 * Licensed Materials - Property of IBM
 * 
 * IBM Cognos Products: CAMAAA
 * 
 * (C) Copyright IBM Corp. 2005, 2012
 * 
 * US Government Users Restricted Rights - Use, duplication or disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 */

import com.cognos.CAM_AAA.authentication.*;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCVisa extends Visa {

	// private ConnectionManager connectionManager;

	private String password;
	private Connection connection;
	private String username;
	private String connectionString;
	private Logger log4j =Logger.getLogger(JDBCVisa.class);
	public JDBCVisa() {
		super();
	}
	public Connection getConnection() {
		     return this.connection;
		  }
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognos.CAM_AAA.provider.IVisa#generateCredential(com.cognos.CAM_AAA
	 * .objectModel.IAuthenticateRequest)
	 */
	@Override
	public ICredential generateCredential(final IBiBusHeader theAuthRequest)
			throws UserRecoverableException, SystemRecoverableException,
			UnrecoverableException {
		if (SimpleQuery.getIt(
				"select usercode from v_s_cdbauth where usercode = '"
						+ this.username + "' or password = '" + this.password
						+ "'", "usercode", ConnectionFactory.USER) != null) {
			UnrecoverableException e = new UnrecoverableException(
					"Could not generate credentials for the user.",
					"Visa contains invalid credentials.");
			log4j.error("不能生成凭证！");
			throw e;
		}
		log4j.info("generateCredential username:"+username+" password:"+password);
		final Credential credentials = new Credential();
		credentials.addCredentialValue("username", this.username);
		credentials.addCredentialValue("password", this.password);
		return credentials;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognos.CAM_AAA.authentication.IVisa#generateTrustedCredential(com
	 * .cognos.CAM_AAA.authentication.IBiBusHeader)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cognos.CAM_AAA.provider.IVisa#generateTrustedCredential(com.cognos
	 * .CAM_AAA.objectModel.IAuthenticateRequest)
	 */
	@Override
	public ITrustedCredential generateTrustedCredential(
			final IBiBusHeader theAuthRequest) throws UserRecoverableException,
			SystemRecoverableException, UnrecoverableException {
		boolean isValidCredentials = false;
		String[] theUsername = null;
		String[] thePassword = null;
		
		theUsername = theAuthRequest.getCredentialValue("username");
		     thePassword = theAuthRequest.getCredentialValue("password");
		    if ((theUsername == null) && (thePassword == null))
		     {
		       theUsername = theAuthRequest.getFormFieldValue("CAMUsername");
		       thePassword = theAuthRequest.getFormFieldValue("CAMPassword");
		     }
		     if ((theUsername != null) && (theUsername.length == 1) && 
		       (theUsername[0].equals(this.username)) && (thePassword.length == 1))
		       isValidCredentials = SimpleQuery.isExistUser(theUsername[0],thePassword[0]); 
		     else {
		       isValidCredentials = SimpleQuery.isExistUser(this.username, this.password); 
		    }
		    
		     if (!isValidCredentials) {
		       UserRecoverableException e = new UserRecoverableException(
		         "Username or password error", 
		         "The provided credentials are invalid.");
		       e.addDisplayObject(
		         new ReadOnlyDisplayObject("User ID:", 
		         "CAMUsername", this.username));
		       e.addDisplayObject(
		         new TextNoEchoDisplayObject("Password:", 
		         "CAMPassword"));
		       log4j.error("请重新输入你的凭证！");
		       throw e;
		     }
		     
		     TrustedCredential tc = new TrustedCredential();
		     tc.addCredentialValue("username", this.username);
		     tc.addCredentialValue("password", this.password);
		     return tc;
	}
	public void init(final JDBCAuth theNamespace, final String theUsername,
			final String thePassword) throws UnrecoverableException {
		try {
//				if (SimpleQuery.getIt(
//						"select usercode from v_s_cdbauth where usercode = '"
//								+ theUsername + "' or password = '"
//								+ thePassword + "'", "usercode",
//						ConnectionFactory.USER) == null) {
//					log4j.error("没有匹配的用户！");
//					UnrecoverableException e = new UnrecoverableException(
//							"Could not generate credentials for the user.",
//							"Visa contains invalid credentials.");
//					throw e;
//				}
				if(!SimpleQuery.isExistUser(theUsername, thePassword)){
					log4j.error("密码错误！");
					UnrecoverableException e = new UnrecoverableException(
							"username or password is wrong!",
							"Visa contains invalid credentials.");
					throw e;
					
				}
			Account account = QueryUtil
					.createAccount(theNamespace, theUsername);
			super.init(account);

			this.username = theUsername;
			this.password = thePassword;
		} catch (SQLException e) {
			log4j.error("数据连接异常！");
			throw new UnrecoverableException("Connection Error",
					"Database connection failure. Reason: " + e.toString());
		}
	}

//	private boolean validateConnection(final Credential credential) {
//		boolean bConnectionValid =false;
//		try { 
//			       Connection newConnection = MS_JDBCDriver.driver.getConnection(
//			         this.connectionString, username, password);
//			      bConnectionValid = true;
//			       this.connection.close();
//			       this.connection = newConnection;
//			       this.username = username;
//			       this.password = password;
//			     } catch (SQLException ex) {
//			       ex.printStackTrace();
//			     }
//		return bConnectionValid;
//	}

	private boolean validateConnection(final String theUsername,final String thePassword) {
		boolean bConnectionValid =false;
		try { 
			       Connection newConnection = MS_JDBCDriver.driver.getConnection(
			         this.connectionString, username, password);
			      bConnectionValid = true;
			       this.connection.close();
			       this.connection = newConnection;
			       this.username = username;
			       this.password = password;
			     } catch (SQLException ex) {
			       ex.printStackTrace();
			     }
		return bConnectionValid;
	}
}
