package lk.ac.ucsc.oms.system_configuration.api;

/**
 * User: Hetti
 * Date: 1/23/13
 * Time: 12:03 PM
 */

/**
 * SystemParameter like BROKER_ID, REPORT_SERVER_URL,....etc
 * string representation of enums
 * <ul>
 * <li>System Parameter BROKER_ID                           </li>
 * <li>System Parameter REPORT_SERVER_URL                   </li>
 * <li>System Parameter BROKER_INST_ID                      </li>
 * <li>System Parameter MIDDLE_WARE_TYPE                    </li>
 * <li>System Parameter TO_STP_QUEUE                        </li>
 * <li>System Parameter RIA_VERSION                         </li>
 * <li>System Parameter EXCHANGE_CONNECT_TYPE               </li>
 * <li>System Parameter IS_PROD                             </li>
 * <li>System Parameter SERVER_IP                           </li>
 * <li>System Parameter ORD_STOPLOSS_MAX_VAL                </li>
 * <li>System Parameter IS_NOTIFY_TO_BACK_OFFICE            </li>
 * <li>System Parameter BACK_OFFICE_CONNECTOR_URL           </li>
 * <li>System Parameter DTL_ENABLED                         </li>
 * <li>System Parameter DTL_START_TIME                      </li>
 * <li>System Parameter DTL_END_TIME                        </li>
 * <li>System Parameter KSE_SPECIALLOGIC_ENABLE             </li>
 * <li>System Parameter DFM_PREOPEN_DELAY                   </li>
 * <li>System Parameter ADSM_PREOPEN_DELAY                  </li>
 * <li>System Parameter TZERO_T2_EOD_DATE                   </li>
 * <li>System Parameter ORDER_FEE_STRUCTURE_MAX_DEPTH       </li>
 * <li>System Parameter LIQUIDATION_REQ_INTERVAL            </li>
 * <li>System Parameter LIQ_TIME_ERROR                      </li>
 * <li>System Parameter SEND_TO_EXCHANGE_FROM_FRONT_OFFICE </li>
 * <li>System Parameter NET_URLr                            </li>
 * <li>System Parameter PRICE_SSO_MODE                      </li>
 * <li>System Parameter PRICE_SSO_VERSION                   </li>
 * <li>System Parameter RSA_KEY_PATH                        </li>
 * <li>System Parameter CHAT_SERVER                         </li>
 * <li>System Parameter NODE_TIME_OUT                       </li>
 * <li>System Parameter IS_CACHE_WRITE_THROUGH              </li>
 * <li>System Parameter SYS_ID                              </li>
 * <li>System Parameter DAY_ORDER_SPECIAL_ROUTING           </li>
 * <li>System Parameter MUSTRIC_EXE_BROKER_ID               </li>
 * <li>System Parameter PARENT_CUSTOMER                     </li>
 * <li>System Parameter DBFS_EXEC_BROKER                    </li>
 * <li>System Parameter DEFAULT_CHAT_SERVER                 </li>
 * <li>System Parameter CURRENCY                            </li>
 * <li>System Parameter SMALL_ORDER_VAL                     </li>
 * <li>System Parameter BSE_IPO_MKT_CODE                    </li>
 * <li>System Parameter BSE_SPMKT_MIN_ORDVAL                </li>
 * <li>System Parameter BSE_SP_MKT_CODE                     </li>
 * <li>System Parameter SIGL_SIGNON_PRIVATE_KEY             </li>
 * <li>System Parameter DEFAULT_SSO_PASSWORD                </li>
 * <li>System Parameter IS_DEALER_CODE_VALIDATE             </li>
 * </ul>
 */
public enum SystemParameter {
    BROKER_ID, REPORT_SERVER_URL, BROKER_INST_ID, MIDDLE_WARE_TYPE, TO_STP_QUEUE, RIA_VERSION, EXCHANGE_CONNECT_TYPE,
    IS_PROD, SERVER_IP, ORD_STOPLOSS_MAX_VAL, IS_NOTIFY_TO_BACK_OFFICE, BACK_OFFICE_CONNECTOR_URL, DTL_ENABLED, DTL_START_TIME,
    DTL_END_TIME, KSE_SPECIALLOGIC_ENABLE, DFM_PREOPEN_DELAY, ADSM_PREOPEN_DELAY, TZERO_T2_EOD_DATE, ORDER_FEE_STRUCTURE_MAX_DEPTH,
    LIQUIDATION_REQ_INTERVAL, LIQ_TIME_ERROR, SEND_TO_EXCHANGE_FROM_FRONT_OFFICE, NET_URL, PRICE_SSO_MODE, PRICE_SSO_VERSION,
    RSA_KEY_PATH, CHAT_SERVER, NODE_TIME_OUT, IS_CACHE_WRITE_THROUGH, SYS_ID, DAY_ORDER_SPECIAL_ROUTING, MUSTRIC_EXE_BROKER_ID, PARENT_CUSTOMER,
    DBFS_EXEC_BROKER, DEFAULT_INSTITUTE_ID, DEFAULT_CHAT_SERVER, CURRENCY, SMALL_ORDER_VAL, BSE_IPO_MKT_CODE, BSE_SPMKT_MIN_ORDVAL, BSE_SP_MKT_CODE,
    ENABLE_OTP_CACHE_VIEWER, MONITOR_APP_URL, ENVIRONMENT, SIGL_SIGNON_PRIVATE_KEY, DEFAULT_SSO_PASSWORD, ENABLE_OTP_SCHEDULER_CONSOLE, IS_ENABLE_SYNC_FAIL_MESSAGES,
    IS_BACK_TO_FRONT_SYNC_MESSAGE_RECORD_ENABLED, IS_DEALER_CODE_VALIDATE
}
