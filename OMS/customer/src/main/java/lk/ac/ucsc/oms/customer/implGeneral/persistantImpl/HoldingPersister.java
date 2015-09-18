package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.common_utility.api.enums.HoldingTypes;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingKey;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingRecord;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;
import lk.ac.ucsc.oms.customer.implGeneral.beans.holding.HoldingRecordBean;

import java.util.Date;
import java.util.List;

public interface HoldingPersister extends CachePersister {

    List<HoldingKey> getCustomerAllHoldingKeys(String customerNumber) throws HoldingManagementException;

    List<CacheObject> getHoldingsForInstitution(String institutions) throws HoldingManagementException;

}
