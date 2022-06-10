package com.bsf.wallet.util;

/**
 * Created by: Tharindu Eranga
 * Date: 07 Jun 2022
 **/
public final class AppConstants {
    public static final String API_VERSION = "v1";

    /*error codes AND messages*/
    public static final Integer SERVICE_ERROR_CODE = 50;
    public static final String SERVICE_ERROR_MESSAGE = "Internal server error";

    public static final Integer ERROR_NO_ACCOUNT_CODE = 44;
    public static final String ERROR_NO_ACCOUNT_MESSAGE = "No account found for id: ";
    public static final Integer ERROR_REFERENCE_CONFLICT_CODE = 45;
    public static final String ERROR_REFERENCE_CONFLICT_MESSAGE = "Reference already exists, please change " +
            "and try again";
    public static final Integer ERROR_SAME_ACCOUNT_TRANSFER_CODE = 46;
    public static final String ERROR_SAME_ACCOUNT_TRANSFER_MESSAGE = "Cannot transfer for the same account";
    public static final Integer ERROR_NOT_ENOUGH_BALANCE_CODE = 47;
    public static final String ERROR_NOT_ENOUGH_BALANCE_MESSAGE = "Not enough balance available for the transaction";

    private AppConstants() {
    }
}
