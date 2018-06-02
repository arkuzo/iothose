/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.arkuzo.Servers;

import static spark.Spark.*;

/**
 *
 * @author arseniy
 */
public class SparkWebPage implements Runnable{
    String addr;

    public SparkWebPage(String addr) {
        this.addr = addr;
    }

    @Override
    public void run() {
        staticFiles.location("/resources/public");
        init();
        get(addr, (req,res)->"Hello world");
    }
    
}
