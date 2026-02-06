package Test101.Components.SFDC;

import Test101.Components.Common;
import base.RunLog;

public class AccountRelatedList extends Common {

    private StringBuilder view(){
        StringBuilder baseURL=new StringBuilder(environment.getValue("sfdc_base_url"));
        return baseURL.append("lightning/r/Account/");
    }

    public void navigateToRecordsListView(String objectNameWithRelation, String accountId){
        String recordViewURL = view().append(accountId + "/related/"+objectNameWithRelation+"/view").toString();
        RunLog.info("Record View URL: "+recordViewURL);
        driver.navigate().to(recordViewURL);
    }

}
