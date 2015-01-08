package com.base.engine.components;

import com.base.engine.core.Input;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Window;

public class FreeLook extends GameComponent {

    private static final Vector3f yAxis = new Vector3f(0,1,0);

    private final int unlockMouseKey;
    boolean mouseLocked = false;
    float sensitivity;
    Vector2f centerPosition = new Vector2f(Window.getWidth()/2, Window.getHeight()/2);

    public FreeLook(){
       this(0.5f);
    }

    public FreeLook(float sensitivity){
       this(sensitivity, Input.KEY_ESCAPE);
    }

    public FreeLook(float sensitivity, int unlockMouseKey){
        this.sensitivity = sensitivity;
        this.unlockMouseKey = unlockMouseKey;
    }

    @Override
    public void input(float delta) {

        if(Input.getKey(unlockMouseKey))
        {
            Input.setCursor(true);
            mouseLocked = false;
        }
        if(Input.getMouseDown(0))
        {
            Input.setMousePosition(centerPosition);
            Input.setCursor(false);
            mouseLocked = true;
        }

        if(mouseLocked) {
            Vector2f deltaPos = Input.getMousePosition().sub(centerPosition);

            boolean rotY = deltaPos.getX() != 0;
            boolean rotX = deltaPos.getY() != 0;

            if(rotY)
                getTransform().rotate(yAxis,
                        deltaPos.getX() * sensitivity);
            if(rotX)
                getTransform().rotate(
                        getTransform().getRotation().getRight(),
                        -deltaPos.getY() * sensitivity);

            if(rotX || rotY)
                Input.setMousePosition(new Vector2f(Window.getWidth()/2, Window.getHeight()/2));
        }
    }





}
