package com.jianjunhuang.lib.switchbutton;

import android.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({Status.INVALID, Status.DEFAULT, Status.CHECKED})
@Retention(RetentionPolicy.RUNTIME)
public @interface Status {
  int INVALID = -1;
  int DEFAULT = 0;
  int CHECKED = 1;
}
