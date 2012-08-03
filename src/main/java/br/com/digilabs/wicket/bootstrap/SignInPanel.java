package br.com.digilabs.wicket.bootstrap;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.authentication.IAuthenticationStrategy;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

public class SignInPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private static final String SIGN_IN_FORM = "signInForm";

	/** True if the panel should display a remember-me checkbox */
	private boolean includeRememberMe = true;

	/** True if the user should be remembered via form persistence (cookies) */
	private boolean rememberMe = true;

	/** password. */
	private String password;

	/** user name. */
	private String username;

	/**
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public SignInPanel(final String id)
	{
		this(id, true);
	}

	/**
	 * @param id
	 *            See Component constructor
	 * @param includeRememberMe
	 *            True if form should include a remember-me checkbox
	 * @see org.apache.wicket.Component#Component(String)
	 */
	public SignInPanel(final String id, final boolean includeRememberMe)
	{
		super(id);

		this.includeRememberMe = includeRememberMe;

		// Add sign-in form to page, passing feedback panel as
		// validation error handler
		add(new SignInForm(SIGN_IN_FORM));
	}

	/**
	 * 
	 * @return signin form
	 */
	protected org.apache.wicket.authroles.authentication.panel.SignInPanel.SignInForm getForm()
	{
		return (org.apache.wicket.authroles.authentication.panel.SignInPanel.SignInForm)get(SIGN_IN_FORM);
	}

	/**
	 * @see org.apache.wicket.Component#onBeforeRender()
	 */
	@Override
	protected void onBeforeRender()
	{
                // logged in already?
		if (isSignedIn() == false)
		{
			IAuthenticationStrategy authenticationStrategy = getApplication().getSecuritySettings()
				.getAuthenticationStrategy();
			// get username and password from persistence store
			String[] data = authenticationStrategy.load();

			if ((data != null) && (data.length > 1))
			{
				// try to sign in the user
				if (signIn(data[0], data[1]))
				{
					username = data[0];
					password = data[1];

					// logon successful. Continue to the original destination
					continueToOriginalDestination();
					// Ups, no original destination. Go to the home page
					throw new RestartResponseException(getSession().getPageFactory().newPage(
						getApplication().getHomePage()));
				}
				else
				{
					// the loaded credentials are wrong. erase them.
					authenticationStrategy.remove();
				}
			}
		}

		// don't forget
		super.onBeforeRender();
	}

	/**
	 * Convenience method to access the password.
	 * 
	 * @return The password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Set the password
	 * 
	 * @param password
	 */
	public void setPassword(final String password)
	{
		this.password = password;
	}

	/**
	 * Convenience method to access the username.
	 * 
	 * @return The user name
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Set the username
	 * 
	 * @param username
	 */
	public void setUsername(final String username)
	{
		this.username = username;
	}

	/**
	 * Get model object of the rememberMe checkbox
	 * 
	 * @return True if user should be remembered in the future
	 */
	public boolean getRememberMe()
	{
		return rememberMe;
	}

	/**
	 * @param rememberMe
	 *            If true, rememberMe will be enabled (username and password will be persisted
	 *            somewhere)
	 */
	public void setRememberMe(final boolean rememberMe)
	{
		this.rememberMe = rememberMe;
	}

	/**
	 * Sign in user if possible.
	 * 
	 * @param username
	 *            The username
	 * @param password
	 *            The password
	 * @return True if signin was successful
	 */
	private boolean signIn(String username, String password)
	{
		return AuthenticatedWebSession.get().signIn(username, password);
	}

	/**
	 * @return true, if signed in
	 */
	private boolean isSignedIn()
	{
		return AuthenticatedWebSession.get().isSignedIn();
	}

	/**
	 * Called when sign in failed
	 */
	protected void onSignInFailed()
	{
		// Try the component based localizer first. If not found try the
		// application localizer. Else use the default
		error(getLocalizer().getString("signInFailed", this, "Sign in failed"));
	}

	/**
	 * Called when sign in was successful
	 */
	protected void onSignInSucceeded()
	{
		// If login has been called because the user was not yet logged in, than continue to the
		// original destination, otherwise to the Home page
		continueToOriginalDestination();
		setResponsePage(getApplication().getHomePage());
	}

	/**
	 * Sign in form.
	 */
	public final class SignInForm extends StatelessForm<SignInPanel>
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor.
		 * 
		 * @param id
		 *            id of the form component
		 */
		public SignInForm(final String id)
		{
			super(id);

			setModel(new CompoundPropertyModel<SignInPanel>(SignInPanel.this));

			// Attach textfields for username and password
			add(new TextField<String>("username"));
			add(new PasswordTextField("password"));

			// MarkupContainer row for remember me checkbox
			WebMarkupContainer rememberMeRow = new WebMarkupContainer("rememberMeRow");
			add(rememberMeRow);

			// Add rememberMe checkbox
			rememberMeRow.add(new CheckBox("rememberMe"));

			// Show remember me checkbox?
			rememberMeRow.setVisible(includeRememberMe);
		}

		/**
		 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
		 */
		@Override
		public final void onSubmit()
		{
			IAuthenticationStrategy strategy = getApplication().getSecuritySettings()
				.getAuthenticationStrategy();

			if (signIn(getUsername(), getPassword()))
			{
				if (rememberMe == true)
				{
					strategy.save(username, password);
				}
				else
				{
					strategy.remove();
				}

				onSignInSucceeded();
			}
			else
			{
				onSignInFailed();
				strategy.remove();
			}
		}
	}
    
}
