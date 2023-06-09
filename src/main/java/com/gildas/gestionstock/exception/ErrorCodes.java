package com.gildas.gestionstock.exception;

public enum ErrorCodes {

    ARTICLE_NOT_FOUND(1000),
    CATEGORIE_NOT_FOUND(2000),
    CLIENT_NOT_FOUND(3000),
    COMMANDE_CLIENT_NOT_FOUND(4000),
    FOURNISSEUR_NOT_FOUND(5000),
    COMMANDE_FOURNISSEUR_NOT_FOUND(6000),
    LIGNE_COMMANDE_CLIENT_NOT_FOUND(7000),
    LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(8000),
    VENTE_NOT_FOUND(9000),
    LIGNE_VENTE_NOT_FOUND(10000),
    MOUVEMENT_STOCK_NOT_FOUND(10000),
    COMMANDE_CLIENT_NOT_EDITABLE(11000),
    COMMANDE_FOURNISSEUR_NOT_EDITABLE(12000),
    UPDATE_PHOTO_EXCEPTION(14000),
    UNKNOW_CONTEXT(15000),
    ARTICLE_ALREADY_IN_USE(16000),
    CATEGORIE_ALREADY_IN_USE(17000),
    COMMANDE_CLIENT_ALREADY_IN_USE(18000);
    private int code;

    ErrorCodes(int code){
        this.code = code;
    }
}
