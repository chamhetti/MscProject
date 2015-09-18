package lk.ac.ucsc.oms.common_utility.api.enums;

/**
 * @author hetti
 */

public enum CacheLoadedState {

    /**
     * When All entity beans are available in cache
     */
    FULLY_LOADED,
    /**
     * when only required beans are loaded in to particular cache
     */
    PARTIALITY_LOADED,
    /**
     * when there is no cached beans
     */
    NOT_LOADED

}
