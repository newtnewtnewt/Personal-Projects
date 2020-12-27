using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;



public class MainMenuScript : MonoBehaviour {


    public void PlayGame() {
        SceneManager.LoadScene(1);

    }

    public void QuitGame() {
        Debug.Log("Quit");
        Application.Quit();
    }
    
    public void goBack()
    {
        SceneManager.LoadScene(0);

    }

    public void plugDebug()
    {
        Debug.Log("I hit the info!");
    }
}
