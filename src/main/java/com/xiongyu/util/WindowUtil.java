package com.xiongyu.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.openapi.wm.ex.WindowManagerEx;

import java.awt.*;

public enum WindowUtil {
  ;

  public static Window getParentWindow(Project project) {
    WindowManagerEx windowManager = (WindowManagerEx) WindowManager.getInstance();
    Window window = windowManager.suggestParentWindow(project);
    if (window == null) {
      Window focusedWindow = windowManager.getMostRecentFocusedWindow();
      if (focusedWindow != null) {
        window = focusedWindow;
      }
    }
    return window;
  }
}
