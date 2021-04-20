package com.viet.exercisethread

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gestureDetector: GestureDetector
    var count: Int = 0
    var thread: HandlerThread = HandlerThread("Thread")
    lateinit var runnablePlus: Runnable
    lateinit var runnableMinus: Runnable
    lateinit var runnableToZero: Runnable
    lateinit var handler: Handler

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        thread.start()
        handler = Handler(thread.looper)

        gestureDetector = GestureDetector(this, this)
        linear.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                handler.removeCallbacks(runnableToZero)
                gestureDetector.onTouchEvent(p1)
                if (p1 != null) {
                    if (p1.action == MotionEvent.ACTION_UP) {
                        handler.postDelayed(runnableToZero, 2000)
                    }
                }

                return true
            }
        })

        btn1.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
                if (motionEvent != null) {
                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                        btn1.setBackgroundResource(R.color.colorBtn2);
                        handler.removeCallbacks(runnableToZero)
                        handler.postDelayed(runnableMinus, 2000)
                    }
                    if (motionEvent.action == MotionEvent.ACTION_UP) {
                        count--
                        textView.text = count.toString()
                        btn1.setBackgroundResource(R.color.colorBtn1);
                        handler.removeCallbacks(runnableMinus)
                        handler.postDelayed(runnableToZero, 2000)
                    }
                }
                return true
            }

        })

        btn2.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
                if (motionEvent != null) {
                    if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                        btn2.setBackgroundResource(R.color.colorBtn2);
                        handler.removeCallbacks(runnableToZero)
                        handler.postDelayed(runnablePlus, 2000)
                    }
                    if (motionEvent.action == MotionEvent.ACTION_UP) {
                        count++
                        textView.text = count.toString()
                        btn2.setBackgroundResource(R.color.colorBtn1);
                        handler.removeCallbacks(runnablePlus)
                        handler.postDelayed(runnableToZero, 2000)
                    }
                }
                return true
            }

        })

        runnableToZero = Runnable {
            if (count > 0) {
                count--
                this.runOnUiThread {
                    textView.text = count.toString()
                }
                changeColor(count)
                handler.postDelayed(runnableToZero, 30)
            } else if (count < 0) {
                count++
                this.runOnUiThread {
                    textView.text = count.toString()
                }
                changeColor(count)
                handler.postDelayed(runnableToZero, 30)
            } else
                handler.removeCallbacks(runnableToZero)
        }

        runnablePlus = Runnable {
            handler.removeCallbacks(runnableToZero)
            count++
            this.runOnUiThread {
                textView.text = count.toString()
            }
            changeColor(count)
            handler.postDelayed(runnablePlus, 30)
        }

        runnableMinus = Runnable {
            handler.removeCallbacks(runnableToZero)
            count--
            this.runOnUiThread {
                textView.text = count.toString()
            }
            changeColor(count)
            handler.postDelayed(runnableMinus, 30)
        }
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onShowPress(p0: MotionEvent?) {

    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return true
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        if (p3 > 0) count++
        else count--
        textView.text = count.toString()
        changeColor(count)
        return true
    }

    override fun onLongPress(p0: MotionEvent?) {
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {

        return true
    }

    fun changeColor(int: Int) {
        var random = Random()
        var color: Int = Color.argb(255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256))
        if (int % 100 == 0 && int != 0)
            this.runOnUiThread {
                textView.setTextColor(color)
            }
    }
}
