package com.xiongyu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiFile;

/**
 * @author xiongyu
 * @version Create at ：2020/7/7 9:42 下午
 */
public class HelloAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project = e.getData(PlatformDataKeys.PROJECT);
        PsiFile psiFile = e.getData(PlatformDataKeys.PSI_FILE);
        String path = psiFile.getVirtualFile().getPath();
        String title = "你好啊";
        Messages.showMessageDialog(project,path,title,Messages.getInformationIcon());
    }
}
