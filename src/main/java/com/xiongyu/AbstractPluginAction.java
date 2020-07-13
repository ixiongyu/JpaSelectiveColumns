package com.xiongyu;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xiongyu.i18n.LocaleItem;

import java.util.Locale;
import java.util.Objects;

/**
 * @author xiongyu
 * @version Create at ：2020/7/13 9:48 下午
 */

public class AbstractPluginAction extends AnAction {
    @Override
    public void update(AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(e.getProject() != null);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Objects.requireNonNull(e.getProject(), "Project inactivated.");

        // 初始化Holder
        ProjectHolder.registerProject(e.getProject());
        ProjectHolder.registerEvent(e);// 注册事件
        ProjectHolder.registerApplicationProperties(PropertiesComponent.getInstance());
        ProjectHolder.registerProjectProperties(PropertiesComponent.getInstance(e.getProject()));
        ProjectHolder.registerDatabaseDrivers(DatabaseDrivers);

        initI18n();
    }

    /**
     * 初始化I18n
     */
    private void initI18n() {
        PropertiesComponent applicationProperties = ProjectHolder.getApplicationProperties();
        // select language
        Locale locale = Locale.forLanguageTag(applicationProperties
                .getValue(createKey("locale"), LocaleContextHolder.getCurrentLocale().toLanguageTag()));
        int localeSelectIndex = -1;
        for (int i = 0; i < LocaleContextHolder.LOCALE_ITEMS.length; i++) {
            LocaleItem localeItem = LocaleContextHolder.LOCALE_ITEMS[i];
            if (localeItem.getLocale().equals(locale)) {
                localeSelectIndex = i;
                break;
            }
        }
        // only compare by language
        if (localeSelectIndex == -1) {
            for (int i = 0; i < LocaleContextHolder.LOCALE_ITEMS.length; i++) {
                LocaleItem localeItem = LocaleContextHolder.LOCALE_ITEMS[i];
                if (localeItem.getLocale().getLanguage().equalsIgnoreCase(locale.getLanguage())) {
                    localeSelectIndex = i;
                    break;
                }
            }
        }
        // not best match language for this locale, reset locale to english
        if (localeSelectIndex == -1) {
            localeSelectIndex = 0;
        }
        LocaleContextHolder.setCurrentLocale(LocaleContextHolder.LOCALE_ITEMS[localeSelectIndex].getLocale());
    }
}
