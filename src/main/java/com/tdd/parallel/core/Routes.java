package com.tdd.parallel.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Routes {
  public static final String ROUTE_CRUD_REPO = "/personCrudRepo";
  public static final String ID_CRUD_REPO = "/{id}";

  public static final String ROUTE_MGO_REPO = "/personMongoRepo";
  public static final String ID_MGO_REPO = "/{id}";

  public static final String ROUTE_RCT_MGO_TPL_REPO = "/personReactMongoTemplRepo";
  public static final String ID_RCT_MGO_TPL_REPO = "/{id}";

  public static final String REQ_MAP_TPL_REPO = "/personTempl";
  public static final String ID_TPL_REPO = "/{id}";
}
