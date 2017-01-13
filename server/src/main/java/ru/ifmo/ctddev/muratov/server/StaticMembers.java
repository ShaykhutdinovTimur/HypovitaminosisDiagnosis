package ru.ifmo.ctddev.muratov.server;

import ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.Model;
import ru.ifmo.ctddev.shaykhutdinov.hypovitaminosisDiagnosis.RegressorInvocator;

/**
 * @author amir.
 */
public class StaticMembers {
    public static Model ImageHandler = new RegressorInvocator();
    public static final String SERVER_FILES_PATH = "serverFiles";
}
