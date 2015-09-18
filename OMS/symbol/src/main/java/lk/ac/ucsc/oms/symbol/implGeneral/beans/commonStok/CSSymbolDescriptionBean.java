package lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok;

import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSSymbolDescription;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.SymbolDescriptionBean;
import org.hibernate.search.annotations.Indexed;

/**
 * Implementation of CSSymbolDescription interface and SymbolDescriptionBean class
 * <p/>
 * User: Hetti
 * Date: 11/23/12
 * Time: 10:40 AM
 */
@Indexed
public class CSSymbolDescriptionBean extends SymbolDescriptionBean implements CSSymbolDescription {

}
