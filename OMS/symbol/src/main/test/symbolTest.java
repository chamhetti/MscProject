import lk.ac.ucsc.oms.common_utility.api.enums.PropertyEnable;
import lk.ac.ucsc.oms.symbol.api.SymbolFacadeFactory;
import lk.ac.ucsc.oms.symbol.api.SymbolManager;
import lk.ac.ucsc.oms.symbol.api.beans.*;
import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSSymbol;
import lk.ac.ucsc.oms.symbol.api.exceptions.SymbolManageException;

/**
 * User: chamindah
 * Date: 4/6/15
 * Time: 9:20 AM
 */
public class symbolTest {
    public  static  void main(String[]args){
        try {
            SymbolFacadeFactory.getInstance().getSymbolManager().initialize();
        } catch (SymbolManageException e) {
            e.printStackTrace();
        }
//        insertCS();
//        updateSymbol();
        loadSymbol();
//        updateSymbol();
//        removeSymbol();
//        getSymbolbyISIN();
//        getAllFromDB();
//        updatePrice();
//        getSymbolByRIC();
//        getSecurityType();

    }

    private static void insertCS() {
        try {
            CSSymbol csSymbol = SymbolFacadeFactory.getInstance().getSymbolManager().getEmptyCSSymbolBean("", "NSDQ");
            csSymbol.setSymbol("VELT");
            csSymbol.setSecurityExchange("NSDQ");
            csSymbol.setSecurityTypes(BaseSymbol.SecurityType.COMMON_STOCK);
            csSymbol.setBuyTPlus(2);
            csSymbol.setCurrency("USD");
            csSymbol.setIsinCode("JE00B41PDC45");
            csSymbol.setReutercode("");
            csSymbol.setMarketCode("ALL");
            csSymbol.setTradeEnable(PropertyEnable.YES);
            csSymbol.setOnlineAllow(PropertyEnable.YES);
            csSymbol.setStatus(BaseSymbol.SymbolStatus.APPROVED);
            csSymbol.setPriceRatio(1.0);
            csSymbol.setNationality("US");


            ShariaInfo shariaInfo = SymbolFacadeFactory.getInstance().getSymbolManager().getEmptyShariaInfoBean(BaseSymbol.SecurityType.COMMON_STOCK);
            shariaInfo.setInsId(1);
            shariaInfo.setShariaEnableOrNot(PropertyEnable.YES);
            csSymbol.getShariaEnableOrganisations().put(shariaInfo.getInsId(), shariaInfo);

            ShariaInfo shariaInfo1 = SymbolFacadeFactory.getInstance().getSymbolManager().getEmptyShariaInfoBean(BaseSymbol.SecurityType.COMMON_STOCK);
            shariaInfo1.setInsId(2);
            shariaInfo1.setShariaEnableOrNot(PropertyEnable.YES);
            csSymbol.getShariaEnableOrganisations().put(shariaInfo1.getInsId(), shariaInfo1);

            BlackListInfo blackListInfo = SymbolFacadeFactory.getInstance().getSymbolManager().getEmptyBlackListInfoBean(BaseSymbol.SecurityType.COMMON_STOCK);
            blackListInfo.setInsId(1);
            blackListInfo.setBlackListed(0);
            csSymbol.getBlackListedOrganisations().put(blackListInfo.getInsId(), blackListInfo);

            BlackListInfo blackListInfo2 = SymbolFacadeFactory.getInstance().getSymbolManager().getEmptyBlackListInfoBean(BaseSymbol.SecurityType.COMMON_STOCK);
            blackListInfo2.setInsId(2);
            blackListInfo2.setBlackListed(0);
            csSymbol.getBlackListedOrganisations().put(blackListInfo2.getInsId(), blackListInfo2);


            SymbolDescription symbolDescription1 = SymbolFacadeFactory.getInstance().getSymbolManager().getEmptySymbolDescriptionBean(BaseSymbol.SecurityType.COMMON_STOCK);
            symbolDescription1.setLanguageCode("EN");
            symbolDescription1.setShortDescription("4170");
            symbolDescription1.setLongDescription("4170");
            csSymbol.getSymbolDescriptions().put(symbolDescription1.getLanguageCode(), symbolDescription1);


            SymbolManager symbolManager = SymbolFacadeFactory.getInstance().getSymbolManager();
            long id = symbolManager.addSymbol(csSymbol);
            System.out.println("ID of the add symbol" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadSymbol() {
        try {
            SymbolManager symbolManager = SymbolFacadeFactory.getInstance().getSymbolManager();
            CSSymbol csSymbol = (CSSymbol) symbolManager.getSymbol("EMAAR", "DFM", BaseSymbol.SecurityType.COMMON_STOCK);
            System.out.println("Loaded CS Symbol : " + csSymbol.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
