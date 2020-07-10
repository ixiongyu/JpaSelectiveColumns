package com.xiongyu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.messages.MessageDialog;

/**
 * @author xiongyu
 * @version Create at ：2020/7/10 8:52 下午
 */
public class GenerateJpaInterface extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        String selectedText = editor.getSelectionModel().getSelectedText();

    }
}
