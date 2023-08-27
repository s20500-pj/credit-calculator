package com.decision.engine.loan.validation;

public interface Validator<D> {

    void throwIfNotValid(D dto);
}
