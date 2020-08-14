package org.vaadin.explorer.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DummyFileData {
    private static final int maximumLevel = 100;
    private static final int maximumElementPerLevel = 70;
    private static final List<DummyFile> DUMMY_FILE_LIST = createDummyFileList();

    private static List<DummyFile> createDummyFileList() {
        return createDummyFileList(null, 0);

    }

    private static List<DummyFile> createDummyFileList(DummyFile parent, int level) {
        List<DummyFile> dummyFileList = new ArrayList<>();
        for (int i = 0; i < maximumElementPerLevel; i++) {
            String code = (parent != null)? parent.getCode() +  "." + i: ""+i;
            DummyFile file = new DummyFile(code, "Item " + code, DummyFileUtil.iconFolder, parent);
            dummyFileList.add(file);
            if (level < maximumLevel) {
               // System.out.println("Level" + level);
                dummyFileList.addAll(createDummyFileList(file, level+1));
            }
        }
        return dummyFileList;
    }

    public List<DummyFile> getDummyFiles() {
        return DUMMY_FILE_LIST;
    }

    public List<DummyFile> getRootDummyFiles() {
        return DUMMY_FILE_LIST.stream()
                .filter(dummyFile -> dummyFile.getParent() == null)
                .collect(Collectors.toList());
    }

    public List<DummyFile> getChildDummyFiles(DummyFile parent) {
        return DUMMY_FILE_LIST.stream().filter(
                dummyFile -> Objects.equals(dummyFile.getParent(), parent))
                .collect(Collectors.toList());
    }

}