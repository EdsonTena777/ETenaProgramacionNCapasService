
package com.digis.com.ETenaProgramacionNCapasMaven.JPA;

import java.util.List;

public class Result<T> {

    public boolean correct;
    public String errorMessage;
    public Exception ex;

    public T object;          // un registro
    public List<T> objects;   // varios registros
}