package org.vaadin.explorer.service;

import org.vaadin.explorer.bean.Account;
import org.vaadin.explorer.bean.AccountData;
import org.vaadin.explorer.bean.DummyFile;
import org.vaadin.explorer.bean.DummyFileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DummyFileService {

    public static String iconFolder = "vaadin:folder-open-o";
    public static String iconFile = "vaadin:file-o";

    private static final int maximumLevel = 100;
    private static final int maximumElementPerLevel = 80;

    public static int getChildCount(DummyFile dummyFile) {
        return hasChildren(dummyFile)?maximumElementPerLevel:0;
    }

    public static Boolean hasChildren(DummyFile dummyFile) {
        if (dummyFile == null) return true;
        return (dummyFile.getLevel() < maximumLevel);
    }

    public static List<DummyFile> fetchChildren(DummyFile parent, int offset) {
        if ( hasChildren(parent)) {
            return createDummyFileList(parent, offset);
        } else {
            return new ArrayList<>();
        }
    }

    private static List<DummyFile> createDummyFileList(DummyFile parent, int offset) {
        List<DummyFile> dummyFileList = new ArrayList<>();
        for (int i = offset; i < maximumElementPerLevel + offset; i++) {
            String code = (parent != null)? parent.getCode() +  "." + i: ""+i;
            String icon = (parent == null)? iconFolder : ((parent.getLevel() + 1  == maximumLevel)?iconFile:iconFolder);
            DummyFile file = new DummyFile(code, "Item " + code, icon, parent);
            dummyFileList.add(file);
        }
        return dummyFileList;
    }
}