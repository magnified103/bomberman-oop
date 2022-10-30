package com.myproject.bomberman.components;

import com.myproject.bomberman.core.Component;

import java.util.HashMap;
import java.util.Map;

public class DataComponent extends Component {

    Map<String, Object> dataMap;

    public DataComponent() {
        dataMap = new HashMap<>();
    }

    public Object getData(String fieldName) {
        if (dataMap.containsKey(fieldName)) {
            return dataMap.get(fieldName);
        }
        throw new RuntimeException(String.format("Field name \"%s\" not found.", fieldName));
    }

    public boolean hasData(String fieldName) {
        return dataMap.containsKey(fieldName);
    }

    public void setData(String fieldName, Object object) {
        dataMap.put(fieldName, object);
    }
}
