package lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok;

import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSBlackListInfo;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.BlackListInfoBean;
import org.hibernate.search.annotations.Indexed;

/**
 * Implementation of CSBlackListInfo interface and BlackListInfoBean class
 * <p/>
 * User: Hetti
 * Date: 11/22/12
 * Time: 11:19 AM
 */
@Indexed
public class CSBlackListInfoBean extends BlackListInfoBean implements CSBlackListInfo {

}
