package com.jiaye.cashloan.http.data.car;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarBrand {

    @SerializedName("A")
    private List<Body> A;
    @SerializedName("B")
    private List<Body> B;
    @SerializedName("C")
    private List<Body> C;
    @SerializedName("D")
    private List<Body> D;
    @SerializedName("E")
    private List<Body> E;
    @SerializedName("F")
    private List<Body> F;
    @SerializedName("G")
    private List<Body> G;
    @SerializedName("H")
    private List<Body> H;
    @SerializedName("I")
    private List<Body> I;
    @SerializedName("J")
    private List<Body> J;
    @SerializedName("K")
    private List<Body> K;
    @SerializedName("L")
    private List<Body> L;
    @SerializedName("M")
    private List<Body> M;
    @SerializedName("N")
    private List<Body> N;
    @SerializedName("O")
    private List<Body> O;
    @SerializedName("P")
    private List<Body> P;
    @SerializedName("Q")
    private List<Body> Q;
    @SerializedName("R")
    private List<Body> R;
    @SerializedName("S")
    private List<Body> S;
    @SerializedName("T")
    private List<Body> T;
    @SerializedName("U")
    private List<Body> U;
    @SerializedName("V")
    private List<Body> V;
    @SerializedName("W")
    private List<Body> W;
    @SerializedName("X")
    private List<Body> X;
    @SerializedName("Y")
    private List<Body> Y;
    @SerializedName("Z")
    private List<Body> Z;

    public List<Body> getA() {
        return A;
    }

    public void setA(List<Body> A) {
        this.A = A;
    }

    public List<Body> getB() {
        return B;
    }

    public void setB(List<Body> B) {
        this.B = B;
    }

    public List<Body> getC() {
        return C;
    }

    public void setC(List<Body> C) {
        this.C = C;
    }

    public List<Body> getD() {
        return D;
    }

    public void setD(List<Body> D) {
        this.D = D;
    }

    public List<Body> getF() {
        return F;
    }

    public void setF(List<Body> F) {
        this.F = F;
    }

    public List<Body> getG() {
        return G;
    }

    public void setG(List<Body> G) {
        this.G = G;
    }

    public List<Body> getH() {
        return H;
    }

    public void setH(List<Body> H) {
        this.H = H;
    }

    public List<Body> getJ() {
        return J;
    }

    public void setJ(List<Body> J) {
        this.J = J;
    }

    public List<Body> getK() {
        return K;
    }

    public void setK(List<Body> K) {
        this.K = K;
    }

    public List<Body> getL() {
        return L;
    }

    public void setL(List<Body> L) {
        this.L = L;
    }

    public List<Body> getM() {
        return M;
    }

    public void setM(List<Body> M) {
        this.M = M;
    }

    public List<Body> getN() {
        return N;
    }

    public void setN(List<Body> N) {
        this.N = N;
    }

    public List<Body> getO() {
        return O;
    }

    public void setO(List<Body> O) {
        this.O = O;
    }

    public List<Body> getP() {
        return P;
    }

    public void setP(List<Body> P) {
        this.P = P;
    }

    public List<Body> getQ() {
        return Q;
    }

    public void setQ(List<Body> Q) {
        this.Q = Q;
    }

    public List<Body> getR() {
        return R;
    }

    public void setR(List<Body> R) {
        this.R = R;
    }

    public List<Body> getS() {
        return S;
    }

    public void setS(List<Body> S) {
        this.S = S;
    }

    public List<Body> getT() {
        return T;
    }

    public void setT(List<Body> T) {
        this.T = T;
    }

    public List<Body> getW() {
        return W;
    }

    public void setW(List<Body> W) {
        this.W = W;
    }

    public List<Body> getX() {
        return X;
    }

    public void setX(List<Body> X) {
        this.X = X;
    }

    public List<Body> getY() {
        return Y;
    }

    public void setY(List<Body> Y) {
        this.Y = Y;
    }

    public List<Body> getZ() {
        return Z;
    }

    public void setZ(List<Body> Z) {
        this.Z = Z;
    }

    public List<Body> getE() {
        return E;
    }

    public void setE(List<Body> e) {
        E = e;
    }

    public List<Body> getI() {
        return I;
    }

    public void setI(List<Body> i) {
        I = i;
    }

    public List<Body> getU() {
        return U;
    }

    public void setU(List<Body> u) {
        U = u;
    }

    public List<Body> getV() {
        return V;
    }

    public void setV(List<Body> v) {
        V = v;
    }

    public static class Body {
        /**
         * "id": "2000194",  //品牌id
         * "big_ppname": "奔驰",  //品牌名称
         * "pin": "B"  //品牌首字母
         */

        @SerializedName("id")
        private String id;
        @SerializedName("big_ppname")
        private String bigPpname;
        @SerializedName("pin")
        private String pin;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBigPpname() {
            return bigPpname;
        }

        public void setBigPpname(String bigPpname) {
            this.bigPpname = bigPpname;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }
    }
}
