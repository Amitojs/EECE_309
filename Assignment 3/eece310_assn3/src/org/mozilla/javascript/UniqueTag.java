package org.mozilla.javascript;

import java.io.Serializable;

/**
 * Class instances represent serializable tags to mark special Object values.
 * <p>
 * Compatibility note: under jdk 1.1 use
 * org.mozilla.javascript.serialize.ScriptableInputStream to read serialized
 * instances of UniqueTag as under this JDK version the default
 * ObjectInputStream would not restore them correctly as it lacks support
 * for readResolve method
 */
public final class UniqueTag implements Serializable
{
    static final long serialVersionUID = -4320556826714577259L;

    private static final int ID_NOT_FOUND    = 1;
    private static final int ID_NULL_VALUE   = 2;
    private static final int ID_DOUBLE_MARK  = 3;

    /**
     * Tag to mark non-existing values.
     */
    public static final UniqueTag
        NOT_FOUND = new UniqueTag(ID_NOT_FOUND);

    /**
     * Tag to distinguish between uninitialized and null values.
     */
    public static final UniqueTag
        NULL_VALUE = new UniqueTag(ID_NULL_VALUE);

    /**
     * Tag to indicate that a object represents "double" with the real value
     * stored somewhere else.
     */
    public static final UniqueTag
        DOUBLE_MARK = new UniqueTag(ID_DOUBLE_MARK);

    private final int tagId;

    private UniqueTag(int tagId)
    {
        this.tagId = tagId;
    }

    //@ signals_only IllegalStateException;
    public Object readResolve()
    {
        switch (tagId) {
          case ID_NOT_FOUND:
            return NOT_FOUND;
          case ID_NULL_VALUE:
            return NULL_VALUE;
          case ID_DOUBLE_MARK:
            return DOUBLE_MARK;
        }
        throw new IllegalStateException(String.valueOf(tagId));
    }
}

