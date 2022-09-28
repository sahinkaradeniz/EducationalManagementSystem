package com.skapps.eys

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.skapps.eys.databinding.ActivityMainBinding
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window=this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.HostFragment)
        navController = navHostFragment!!.findNavController()
   //     ExpandableBottomBarNavigationUI.setupWithNavController(binding.bottomNav,navController)
        ExpandableBottomBarNavigationUI.setupWithNavController(binding.bottomNavTeacher,navController)
        setContentView(view)

        val toolbarNavigationView=findViewById<TextView>(R.id.toolbarTitle)
        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNav.visibility = if (destination.id==R.id.loginFragment || destination.id==R.id.signUpFragment) {
                View.GONE
            } else{
                if(destination.id==R.id.homeFragment){
                    toolbarNavigationView.text="Ödevler"
                setStatusBar(R.color.white)
                }else if(destination.id==R.id.loginFragment){
                    toolbarNavigationView.visibility=View.GONE
                }else if(destination.id==R.id.signUpFragment){
                    toolbarNavigationView.visibility=View.GONE
                }else if(destination.id==R.id.forumFragment){
                    toolbarNavigationView.text="Forum"
                }else if(destination.id==R.id.classesFragment){
                    toolbarNavigationView.text="Sınıflar"
                }else if(destination.id==R.id.homeTeacherFragment){
                    toolbarNavigationView.text="Ödevlendirme"
                }else if(destination.id==R.id.classesTeacherFragment){
                    toolbarNavigationView.text="Sınıflar"
                }else if(destination.id==R.id.settingsTeacherFragment ||destination.id==R.id.settingsFragment){
                    toolbarNavigationView.text="Ayarlar"
                }else{
                    toolbarNavigationView.text="İsimsiz"
                }
                View.VISIBLE
            }
        }

    }
    private fun setStatusBar(color:Int){
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this,color)
    }
}