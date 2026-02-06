package Test101.Components.SFDC.Login;

import base.RunLog;
import com.sforce.soap.partner.LoginResult;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;

public class SalesforceConnection{

    private PartnerConnection con;
    private LoginResult loginResult;
    private final String username;
    private final String password;
    private final String loginUrl;
    private String sessionId;
    private String serverUrl;

    public SalesforceConnection(final String username, final String password, final String loginUrl) {
        this.username = username;
        this.password = password;
        this.loginUrl = loginUrl;
    }

    public String createNewSession() throws ConnectionException {
        loginResult = login();
        sessionId = loginResult.getSessionId();
        serverUrl = loginResult.getServerUrl();
        return sessionId;
    }

    public String getSessionId() {

        if(sessionId != null) {
            return sessionId;
        }
        else if(loginResult != null) {
            sessionId = loginResult.getSessionId();
            return sessionId;
        }
        return null;
    }

    private LoginResult login() throws ConnectionException {
        ConnectorConfig config = new ConnectorConfig();
        config.setAuthEndpoint(loginUrl);
        config.setServiceEndpoint(loginUrl);
        config.setManualLogin(true);

        con = new PartnerConnection(config);

        RunLog.info("Login URL: " + loginUrl);
        return con.login(username, password);
    }

    public void closeSession() throws ConnectionException {
        if(con != null) {
            con.logout();
            sessionId = null;
            serverUrl = null;
        }
    }

}