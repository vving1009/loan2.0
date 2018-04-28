package com.jiaye.cashloan.http.data.loan;

import com.google.gson.annotations.SerializedName;
import com.jiaye.cashloan.http.base.SatcatcheChildResponse;

/**
 * ContractList
 *
 * @author 贾博瑄
 */

public class ContractList extends SatcatcheChildResponse {

    @SerializedName("compact_names")
    private Contract[] contracts;

    public Contract[] getContracts() {
        return contracts;
    }

    public void setContracts(Contract[] contracts) {
        this.contracts = contracts;
    }

    public static class Contract {

        @SerializedName("el_id")
        private String id;

        @SerializedName("compact_name")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
