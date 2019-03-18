package com.game.framework.dataprovier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.game.framework.exception.StaticDataException;
import com.game.framework.util.StringUtil;
import com.game.framework.util.StringUtil.LanguageEnum;

/**
 *
 */
public abstract class AbstractStaticDataProvider<T extends StaticData> {
    /** */
    protected File staticDataFile = null;

    /** 字段注释所在有效行 */
    private int fieldAnnotationRow = 0;
    /** 字段名所在有效行*/
    private int fieldNameRow = 0;
    /** 字段类型所在有效行 */
    private int fieldTypeRow = 0;

    /**
     *
     * @param staticDataFile
     * @param fieldAnnotationRow
     * @param fieldNameRow
     * @param fieldTypeRow
     */
    public AbstractStaticDataProvider(File staticDataFile,
                                      int fieldAnnotationRow, int fieldNameRow, int fieldTypeRow ) {
        this.staticDataFile = staticDataFile;
        this.fieldAnnotationRow = fieldAnnotationRow;
        this.fieldNameRow = fieldNameRow;
        this.fieldTypeRow = fieldTypeRow;
    }

    public static enum SupportType{
        Int(0),
        Short(1),
        Byte(2),
        Long(3),
        Float(4),
        Double(5),
        Boolean(6),
        String(7),
        String_FenHao(8),
        String_DouHao_FenHao(9),
        String_DouHao_FenHao_ShuXian(10);
        private SupportType( int value ) {
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }
    }

    public static final String[] SUPPORT_TYPE_ARRAY =
            new String[]{"Int", "Short", "Byte", "Long", "Float", "Double", "Boolean",
                    "String", "String_FenHao", "String_DouHao_FenHao", "String_DouHao_FenHao_ShuXian"};

    public AbstractStaticDataProvider( File staticDataFile ) {
        this( staticDataFile, 1, 2, 3 );
    }

    @SuppressWarnings("resource")
    public AbstractStaticDataProvider<T> loader() throws StaticDataException {
        //
        String[] fieldNameArray = null;
        //
        String[] fieldTypeArray = null;

        ArrayList<Object[]> fieldValueObjArrayList = new ArrayList<>();

        FileInputStream rawIn = null;
        BufferedReader reader = null;

        String split = "\t";


        //	这里是解析和验证数据有效性的部分

        try {
            rawIn = new FileInputStream(staticDataFile);
            reader = new BufferedReader(new InputStreamReader(rawIn, "UTF-8"));
            String line = null;
            int counter = 0;

            for (; (line = reader.readLine()) != null;) {

                if ((line = line.trim()).isEmpty()) {
                    continue;
                }

                if ((line.startsWith("#"))) {
                    continue;
                }

                ++counter;
//				System.out.println("counter " + counter);
                if ( counter == fieldAnnotationRow ) {
                    continue;
                } else if ( counter == fieldNameRow ) {
                    String[] nameArray = line.split(split);
                    String name = null;

                    fieldNameArray = new String[nameArray.length];

                    for( int i = 0; i < nameArray.length; i++ ) {

                        if( (name = nameArray[i].trim()).isEmpty() ) {
                            throw new StaticDataException("Name must not null");
                        }

                        if( !StringUtil.checkFieldName(LanguageEnum.JAVA, name) ) {
                            throw new StaticDataException("Name is not java field name");
                        }

                        fieldNameArray[i] = name;
                    }

                } else if ( counter == fieldTypeRow ) {
                    String[] typeArray = line.split(split);
                    String type = null;
                    fieldTypeArray = new String[typeArray.length];

                    for( int i = 0; i < typeArray.length; i++ ) {

                        if( (type = typeArray[i].trim()).isEmpty() ) {
                            throw new StaticDataException("Type must not null");
                        }

                        if( !StringUtil.contains(SUPPORT_TYPE_ARRAY, type) ) {
                            throw new StaticDataException("Type is not java field name");
                        }

                        fieldTypeArray[i] = type;
                    }

                } else {
                    String[] strarray = line.split(split);
                    Object[] objarray = new Object[strarray.length];

                    String fieldType = null;

                    for( int i = 0; i < strarray.length; i++ ) {
                        fieldType = fieldTypeArray[i];
                        if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Short.value])) {
                            objarray[i] = Short.valueOf(strarray[i]);
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Int.value])) {
                            objarray[i] = Integer.valueOf(strarray[i]);
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Long.value])) {
                            objarray[i] = Long.valueOf(strarray[i]);
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Boolean.value])) {
                            objarray[i] = Boolean.valueOf(strarray[i].toLowerCase());
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Byte.value])) {
                            objarray[i] = Byte.valueOf(strarray[i]);
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Float.value])) {
                            objarray[i] = Float.valueOf(strarray[i]);
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.Double.value])) {
                            objarray[i] = Double.valueOf(strarray[i]);
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.String.value])) {
                            objarray[i] = strarray[i];
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.String_DouHao_FenHao.value])) {
                            objarray[i] = null;
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.String_DouHao_FenHao_ShuXian.value])) {
                            objarray[i] = null;
                        } else if( fieldType.equals(SUPPORT_TYPE_ARRAY[SupportType.String_FenHao.value])) {
                            objarray[i] = null;
                        }
                    }
                    //	从第四行开始, 每行数据添加进list
                    fieldValueObjArrayList.add(objarray);

                }

            }

        } catch (IOException e) {
            throw new StaticDataException(e);
        } finally {
            try {
                if (rawIn != null) {
                    rawIn.close();
                }
            } catch (Exception e1) {
                throw new StaticDataException(e1);
            }
        }

        //	这里对数据进行一部验证

        int fieldLength = 0;

        if( fieldNameArray.length != fieldTypeArray.length ) {
            throw new StaticDataException("Name And Type Group Length Error");
        }

        fieldLength = fieldNameArray.length;

        for( int i = 0, size = fieldValueObjArrayList.size(); i < size; i++ ) {
            if( fieldLength != fieldValueObjArrayList.get(i).length ) {
                throw new StaticDataException("Data Group Length Error" );
            }
        }

        //	这里是将创造静态数据的部分, 子类需要实现create函数并将结果放入缓存中
        for (int i = 0, size = fieldValueObjArrayList.size(); i < size; i++) {
            createStaticData(fieldNameArray, fieldValueObjArrayList.get(i));
        }

        return this;
    }

    protected abstract void createStaticData(String[] fieldNameArray, Object[] fieldValueArray)
            throws StaticDataException;

    public Object getStaticData() {
        throw new UnsupportedOperationException();
    }

    public Object getStaticData( Object key ) {
        throw new UnsupportedOperationException();
    }

    public Object getStaticData( Object key1, Object key2 ) {
        throw new UnsupportedOperationException();
    }

    public Object getStaticData( Object key1, Object key2, Object key3 ) {
        throw new UnsupportedOperationException();
    }

    public List<Object> getAllStaticData() {
        throw new UnsupportedOperationException();
    }
    public Object getAllStaticDataObject() {
        throw new UnsupportedOperationException();
    }
}
