package com.lagou;

import com.lagou.pojo.SqlCommandType;
import org.junit.Test;

public class IPersistenceTest {

    @Test
    public void test1(){

        for(SqlCommandType sct: SqlCommandType.values()){

            System.out.println(sct.name());
        }


    }

}
