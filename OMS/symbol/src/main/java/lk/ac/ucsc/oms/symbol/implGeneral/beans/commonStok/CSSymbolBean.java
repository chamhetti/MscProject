package lk.ac.ucsc.oms.symbol.implGeneral.beans.commonStok;

import lk.ac.ucsc.oms.symbol.api.beans.commonStok.CSSymbol;
import lk.ac.ucsc.oms.symbol.implGeneral.beans.BaseSymbolBean;
import org.hibernate.search.annotations.Indexed;

/**
 * Implementation of CSSymbol interface and BaseSymbolBean class
 * <p/>
 * User: Hetti
 * Date: 11/19/12
 * Time: 1:47 PM
 */
@Indexed
public class CSSymbolBean extends BaseSymbolBean implements CSSymbol {


    /**
     * Default constructor with private access modifier
     * to avoid being instance creation.
     */
    private CSSymbolBean() {

    }

    /**
     * Constructor with symbol and exchange as constructor arguments.
     * Security type will be provided as CS
     *
     * @param symbol   code
     * @param exchange code
     */
    public CSSymbolBean(String symbol, String exchange) {
        super(symbol, exchange, SecurityType.COMMON_STOCK);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
