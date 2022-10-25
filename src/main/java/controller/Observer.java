package controller;

/**
 *
 * @author Usuario
 */
public interface Observer {

    public void updateCarPosition();

    public void updateControllStatus(boolean status);

    public void updateThreadCounter(int counter);

}
