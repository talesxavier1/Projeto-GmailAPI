package GmailAPI.GmailAPI;

public class User 
{
	private String name,
	               credentialPath,
	               accessToken,
	               refreshToken,
	               scope;
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCredentialPath()
	{
		return credentialPath;
	}

	public void setCredentialPath(String credentialPath)
	{
		this.credentialPath = credentialPath;
	}

	public String getAccessToken()
	{
		return accessToken;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getRefreshToken()
	{
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken)
	{
		this.refreshToken = refreshToken;
	}

	public String getScope()
	{
		return scope;
	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}
}
