package lk.ac.ucsc.oms.customer.implGeneral.persistantImpl;

import lk.ac.ucsc.oms.cache.api.CachePersister;
import lk.ac.ucsc.oms.cache.api.beans.CacheObject;
import lk.ac.ucsc.oms.customer.api.beans.holding.HoldingLog;
import lk.ac.ucsc.oms.customer.api.exceptions.HoldingManagementException;

import java.util.Date;
import java.util.List;


public interface HoldingLogPersister extends CachePersister {

    List<CacheObject> loadWithInstitutionDateFilter(String institutionList, Date fromDate, Date toDate);

}
