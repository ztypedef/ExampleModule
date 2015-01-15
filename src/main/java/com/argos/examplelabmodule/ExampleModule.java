/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.argos.examplelabmodule;

import com.argos.labgui.gui.perspective.Perspective;
import com.argos.labmaster.ElectronicLoad;
import com.argos.labmaster.module.ModuleLab;
import java.awt.Color;

/**
 *
 * @author Zaparivanny Pavel
 */
public class ExampleModule extends ModuleLab{
    
    
    public ExampleModule() {
        Perspective pesp = new Perspective("view_3");
        setPerspective(pesp);
        int slaveAddress = 1;
        addDevice(new ExampleDevice(slaveAddress, pesp));
        
        ElectronicLoad el = new ElectronicLoad(slaveAddress);
        pesp.setBackground(Color.ORANGE);
        el = new ElectronicLoad(slaveAddress);
        pesp.addUnit(el);
        pesp.addUnit(new ElectronicLoad(2));
        pesp.addUnit(new ElectronicLoad(3));
    }
    
}
