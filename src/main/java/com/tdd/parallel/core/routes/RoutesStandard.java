package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesStandard {
  public static final String REQ_MAP_STD = "/personTemplStandard";
  public static final String CRUD_STD = "/personCrudRepoStandard";
  public static final String REPO_STD = "/personMongoRepoStandard";
  public static final String TPL_STD = "/personReactMongoTemplRepoStandard";
  public static final String ID_STD = "/{id}";
}
