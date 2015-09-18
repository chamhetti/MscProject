package lk.ac.ucsc.oms.orderMgt.implGeneral.facade;

import lk.ac.ucsc.oms.common_utility.api.enums.*;
import lk.ac.ucsc.oms.orderMgt.api.OrderRejectReason;
import lk.ac.ucsc.oms.orderMgt.api.beans.Order;
import lk.ac.ucsc.oms.orderMgt.api.beans.OrderValidationReply;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderException;
import lk.ac.ucsc.oms.orderMgt.api.exceptions.InvalidOrderStatusException;
import lk.ac.ucsc.oms.orderMgt.implGeneral.bean.OrderValidationReplyBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;


public final class OrderValidator {

    private static String moduleCode;
    private static Logger logger = LogManager.getLogger(OrderValidator.class);

    private OrderValidator() {

    }


    public static void setModuleCode(String moduleCode) {
        OrderValidator.moduleCode = moduleCode;
    }


    static void validateOrderCreation(Order ord) throws InvalidOrderException {
        if (ord == null) {
            logger.error("order is null");
            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.PASSED_OBJECT_IS_NULL.getCode()));
        }

        if (ord.getStatus() == OrderStatus.REJECTED) {
            logger.info("Rejected order hence validateOrderCreation is skipped");
            return;
        }

        if (ord.getSymbol() == null) {
            logger.error("symbol is invalid. symbol=" + ord.getSymbol());
            setOrderRejectReasons(ord, OrderRejectReason.INVALID_SYMBOL.getCode(), null, "symbol is invalid", "Request does not contain the symbol");
            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.NO_SYMBOL_FOUND.getCode()));
        }
        if (ord.getSide() == null) {
            logger.error("side is invalid. side=" + ord.getSide());
            setOrderRejectReasons(ord, OrderRejectReason.INVALID_SIDE.getCode(), null, "side is invalid", "Request does not contain the side");
            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.INVALID_ORDER_SIDE.getCode()));
        }
        if (ord.getQuantity() <= 0) {
            logger.error("Qty is invalid. qty=" + ord.getQuantity());
            setOrderRejectReasons(ord, ErrorOutput.INVALID_ORDER_QTY.getCode(), null, "Invalid order qty", "Order qty can't be zero or less than zero");
            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.INVALID_ORDER_QTY.getCode()));
        }
        if (ord.getType() == null) {
            logger.error("type is invalid. type=" + ord.getType());
            setOrderRejectReasons(ord, ErrorOutput.INVALID_ORDER_TYPE.getCode(), null, "Invalid order type", "Order type can't be null or empty");

            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.INVALID_ORDER_TYPE.getCode()));
        }
        if (ord.getSecurityAccount() == null) {
            logger.error("securityAccount is invalid. securityAcc=" + ord.getSecurityAccount());
            setOrderRejectReasons(ord, ErrorOutput.INVALID_ORDER_TYPE.getCode(), null, "Invalid Account", "Customer account can't be null or empty");

            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.NO_SECURITY_ACC_FOUND.getCode()));
        }

    }


    static void setOrderRejectReasons(Order order, int errorCode, String errorMessageParamList, String rejectReason, String internalRejReason) {
        order.setErrorCode(errorCode);
        order.setErrMsgParameters(errorMessageParamList);
        order.setText(rejectReason);
        order.setInternalRejReason(internalRejReason);
    }


    static void validateOrderID(String clOrderID) throws InvalidOrderException {
        if (clOrderID == null || clOrderID.length() == 0) {
            logger.error("incorrect clOrder id " + clOrderID);
            throw new InvalidOrderException(getErrMsg(moduleCode, ErrorOutput.INVALID_ORDER_ID.getCode()));
        }
    }


    /**
     * get error message as a string. concat module code and error code with colon in between and return it as a string
     *
     * @param moduleCode of the module
     * @param errCode    to concat with module code
     * @return errorMessage
     */
    private static String getErrMsg(String moduleCode, int errCode) {
        return moduleCode.concat(": ") + errCode;
    }


    static OrderValidationReply isValidAmendOrder(Order amendOrder, Order oldOrder) {
        OrderValidationReply reply = new OrderValidationReplyBean();
        String errorMsgParameterList = null;
        //manual orders are not allow to change
        if (oldOrder.getChannel() == ClientChannel.MANUAL) {
            logger.info("Manual Orders not allow to Amend");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Manual Orders not allow to Amend", errorMsgParameterList);
        }
        //Only open orders are allow to Amend
        if (oldOrder.getStatus() != OrderStatus.NEW && oldOrder.getStatus() != OrderStatus.PARTIALLY_FILLED &&
                oldOrder.getStatus() != OrderStatus.REPLACED && oldOrder.getStatus() != OrderStatus.PROCESSING) {
            logger.info("Amend does not allow - Order is not open");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Amend does not allow - Order is not open", errorMsgParameterList);
        }
        //Fix order are allow to Amend only through a fix order
        if (oldOrder.getChannel() == ClientChannel.FIX && amendOrder.getChannel() != ClientChannel.FIX) {
            logger.info("Fix orders does not allow to amend from other channel");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Fix orders does not allow to amend from other channel", errorMsgParameterList);
        }
        //Reject of the amend Qty is zero or less
        if (amendOrder.getQuantity() <= 0) {
            logger.info("Invalid amend qty");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Invalid amend qty ", errorMsgParameterList);
        }

        //Not allow to amend to a qty less than current filled qty
        if (oldOrder.getCumQty() >= amendOrder.getQuantity()) {
            logger.info("Not allow to amend to lower qty than filled qty");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Not allow to amend to lower qty than filled qty", errorMsgParameterList);
        }
        //square off orders are not allow to amend
        if (oldOrder.getType() == OrderType.SQUARE_OFF && amendOrder.getChannel() != ClientChannel.COND) {
            logger.info("Square off orders are not allow to amend.");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Square off orders are not allow to amend. Cancel and put a new order", errorMsgParameterList);
        }
        //check whether the original order and the amend order both are day orders. If either order is not a day order then amend should reject
        if ((oldOrder.isDayOrder() && !amendOrder.isDayOrder()) || (!oldOrder.isDayOrder() && amendOrder.isDayOrder())) {
            logger.info("Order is not allow to amend.");
            errorMsgParameterList = amendOrder.getExchange() + ",";
            return new OrderValidationReplyBean(Status.FAILED,
                    "Order is not allow to amend.", errorMsgParameterList);
        }

        reply.setStatus(Status.SUCCESS);
        return reply;
    }

    static OrderValidationReply isValidCancelOrder(Order cancelOrder, Order oldOrder) {
        OrderValidationReply reply = new OrderValidationReplyBean();
        //Only open orders are allow to Cancel
        if (oldOrder.getStatus() != OrderStatus.NEW && oldOrder.getStatus() != OrderStatus.PARTIALLY_FILLED &&
                oldOrder.getStatus() != OrderStatus.REPLACED && oldOrder.getStatus() != OrderStatus.OMS_ACCEPTED
                && oldOrder.getStatus() != OrderStatus.SEND_TO_OMS_NEW && oldOrder.getStatus() != OrderStatus.PROCESSING
                && oldOrder.getStatus() != OrderStatus.VALIDATED && oldOrder.getStatus() != OrderStatus.PENDING_TRIGGER
                && oldOrder.getStatus() != OrderStatus.PENDING_NEW) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Cancel does not allow - Order is not open");
        }

        //square off orders are not allow to cancel only from AT and DT
        if (oldOrder.getType() == OrderType.SQUARE_OFF && cancelOrder.getChannel() != ClientChannel.COND && cancelOrder.getChannel() != ClientChannel.AT &&
                cancelOrder.getChannel() != ClientChannel.DT) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Square off orders are not allow to Cancel");
        }
        reply.setStatus(Status.SUCCESS);
        return reply;
    }


    static OrderValidationReply isValidOrder(Order order) {
        OrderValidationReply reply = new OrderValidationReplyBean();
        //Should have valid order qty
        if (order.getQuantity() <= 0) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Order qty should be grater than zero");
        }
        //Min qty should be less than the order qty
        if (order.getMinQty() > 0 && (order.getMinQty() > order.getQuantity())) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Min qty should be less than or equal to the order qty");
        }

        //Max floor should be less than or equal to order qty
        if (order.getMaxFloor() > 0 && (order.getMaxFloor() > order.getQuantity())) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Max floor should be equal or less than to the order quantity");
        }
        //limit order should have valid price
        if (order.getType() == OrderType.LIMIT && order.getPrice() <= 0) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Price of the limit order should be greater than zero");
        }
        //Day orders are not allow for CASE due to tplus
        if (order.isDayOrder() && "CASE".equalsIgnoreCase(order.getExchange())) {
            return new OrderValidationReplyBean(Status.FAILED,
                    "Day Orders are not allow for CASE");
        }
        if (order.getTimeInForce() == OrderTIF.GTD.getCode()) {
            if (order.getExpireTime() == null) {
                return new OrderValidationReplyBean(Status.FAILED,
                        "Expire date can't be null for GTD orders");
            }
            if (order.getExpireTime().before(new Date())) {
                return new OrderValidationReplyBean(Status.FAILED,
                        "Expire date should be a future date");
            }
        }
        reply.setStatus(Status.SUCCESS);
        return reply;
    }


    enum ErrorOutput {
        NO_SYMBOL_FOUND(-1),
        INVALID_ORDER_SIDE(-2),
        INVALID_ORDER_QTY(-3),
        INVALID_ORDER_TYPE(-4),
        INVALID_ORDER_ID(-5),
        NO_SECURITY_ACC_FOUND(-6),
        CANNOT_AMEND(-7),
        INVALID_PRICE_FOR_LIMIT_ORDER(-8),
        PASSED_OBJECT_IS_NULL(-9),
        CANNOT_EXPIRE(-10);

        private int code;

        /**
         * constructor for enum class
         *
         * @param code of error
         */
        ErrorOutput(int code) {
            this.code = code;
        }

        /**
         * to get int error code
         *
         * @return errorCode
         */
        public int getCode() {
            return code;
        }

        /**
         * to get ErrorOutput enum given the errorCode
         *
         * @param code of error
         * @return errorOutput
         */
        public static ErrorOutput getEnum(int code) {
            final int noSymbolConst = -1;
            final int invalOrdSideConst = -2;
            final int invalOrdQtyConst = -3;
            final int invalOrdTypeConst = -4;
            final int invalOrdIdConst = -5;
            final int noSecAccConst = -6;
            final int cannotAmendConst = -7;
            final int invalPriceConst = -8;
            final int passedObjNullConst = -9;
            final int cannotExpireConst = -10;
            switch (code) {
                case noSymbolConst:
                    return NO_SYMBOL_FOUND;
                case invalOrdSideConst:
                    return INVALID_ORDER_SIDE;
                case invalOrdQtyConst:
                    return INVALID_ORDER_QTY;
                case invalOrdTypeConst:
                    return INVALID_ORDER_TYPE;
                case invalOrdIdConst:
                    return INVALID_ORDER_ID;
                case noSecAccConst:
                    return NO_SECURITY_ACC_FOUND;
                case cannotAmendConst:
                    return CANNOT_AMEND;
                case invalPriceConst:
                    return INVALID_PRICE_FOR_LIMIT_ORDER;
                case passedObjNullConst:
                    return PASSED_OBJECT_IS_NULL;
                case cannotExpireConst:
                    return CANNOT_EXPIRE;
                default:
                    return null;
            }
        }
    }
}
