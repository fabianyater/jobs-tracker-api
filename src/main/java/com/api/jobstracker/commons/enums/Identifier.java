package com.api.jobstracker.commons.enums;

import lombok.Getter;

@Getter
public enum Identifier {
  COMPANY("CPY"),
  APPLICATION("APT"),
  JOB("JOB"),
  COMMENT("CMT"),
  STATUS("STS"),
  APPLICATION_STATUS("AST");

  private final String code;

  /**
   * @param code código
   */
  Identifier(String code) {
    this.code = code;
  }
}
