package com.jianjunhuang.lib.switchbutton;


import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({SwitchButtonStatus.INVALID, SwitchButtonStatus.DEFAULT, SwitchButtonStatus.CHECKED})
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchButtonStatus {
  int INVALID = -1;
  int DEFAULT = 0;
  int CHECKED = 1;
}
