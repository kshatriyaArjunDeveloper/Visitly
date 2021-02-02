package com.example.visitly;

import android.net.Uri;

public class FC_member {

    private String NAME;
    private String AGE;
    private String GENDER;
    private String M_NUMBER;
    private String PASSPORT;
    private String PNR;
    private String VERIFICATION;
    private Uri person_image_uri = null;
    private Uri identity1_image_uri = null;
    private Uri identity2_image_uri = null;

    public FC_member() {
    }

    public String getNAME() {
        return NAME;
    }

    public String getM_NUMBER() {
        return M_NUMBER;
    }

    public String getAGE() {
        return AGE;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public void setM_NUMBER(String m_NUMBER) {
        this.M_NUMBER = m_NUMBER;
    }

    public Uri getPerson_image_uri() {
        return person_image_uri;
    }

    public void setPerson_image_uri(Uri person_image_uri) {
        this.person_image_uri = person_image_uri;
    }

    public Uri getIdentity1_image_uri() {
        return identity1_image_uri;
    }

    public Uri getIdentity2_image_uri() {
        return identity2_image_uri;
    }

    public void setIdentity1_image_uri(Uri identity1_image_uri) {
        this.identity1_image_uri = identity1_image_uri;
    }

    public void setIdentity2_image_uri(Uri identity2_image_uri) {
        this.identity2_image_uri = identity2_image_uri;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getPASSPORT() {
        return PASSPORT;
    }

    public void setPASSPORT(String PASSPORT) {
        this.PASSPORT = PASSPORT;
    }

    public String getPNR() {
        return PNR;
    }

    public void setPNR(String PNR) {
        this.PNR = PNR;
    }

    public String getVERIFICATION() {
        return VERIFICATION;
    }

    public void setVERIFICATION(String VERIFICATION) {
        this.VERIFICATION = VERIFICATION;
    }
}

