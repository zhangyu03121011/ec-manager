package com.common.ecmanager.common.utils;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

public class ProtobufSerializeUtils{
	
	/**
	 * Protobuf框架序列化对象
	 * @param t
	 * @param clazz
	 * @return
	 */
    public static <T> byte[] serialize(T t,Class<T> clazz) {  
        return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),  
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));  
    } 
    
    /**
	 * Protobuf框架反序列化对象
	 * @param t
	 * @param clazz
	 * @return
	 */
    public static <T> T deSerialize(byte[] data,Class<T> clazz) {  
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);  
        T t = runtimeSchema.newMessage();  
        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);  
        return t;  
    }  
}  