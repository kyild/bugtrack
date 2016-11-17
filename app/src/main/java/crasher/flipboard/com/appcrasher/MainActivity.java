package crasher.flipboard.com.appcrasher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bugsnag.android.Bugsnag;

import rx.Observable;
import rx.Subscriber;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bugsnag.init(this);
        Button outOfBounds = (Button) findViewById(R.id.out_of_bounds);
        outOfBounds.setOnClickListener(this);

        Button outOfMemory = (Button) findViewById(R.id.out_of_memory);
        outOfMemory.setOnClickListener(this);

        Button runTime = (Button) findViewById(R.id.run_time);
        runTime.setOnClickListener(this);

        Button nulPointer = (Button) findViewById(R.id.null_pointer);
        nulPointer.setOnClickListener(this);

        Button freeze = (Button) findViewById(R.id.freeze);
        freeze.setOnClickListener(this);

        Button stackOverflow = (Button) findViewById(R.id.stack_overflow);
        stackOverflow.setOnClickListener(this);

        Button kotlin = (Button) findViewById(R.id.kotlin);
        kotlin.setOnClickListener(this);

        Button rxJavaStream = (Button) findViewById(R.id.rx_java_stream);
        rxJavaStream.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.out_of_bounds:
                crashOutOfBound();
                break;
            case R.id.out_of_memory:
                crashOutOfMemory();
                break;
            case R.id.run_time:
                runTime();
                break;
            case R.id.null_pointer:
                nullPointer();
                break;
            case R.id.freeze:
                freeze();
                break;
            case R.id.stack_overflow:
                stackOverflow();
                break;
            case R.id.kotlin:
                kotlinCrash();
                break;
            case R.id.rx_java_stream:
                rxJavaStreamCrash();
                break;
        }
    }

    private void rxJavaStreamCrash() {

        integerObservable.subscribe(integerSubscriber);


    }

    private Subscriber integerSubscriber = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
            System.out.println("Complete!");
        }

        @Override
        public void onError(Throwable e) {
            throw new RuntimeException(e);
        }

        @Override
        public void onNext(Integer integer) {

        }

    };


    private Observable integerObservable = Observable.create(new Observable.OnSubscribe() {
        @Override
        public void call(Object o) {
            integerSubscriber.onNext(1);
            integerSubscriber.onNext("kemal");
            integerSubscriber.onCompleted();
        }
    });

    private static void kotlinCrash() {
        KotlinCrasher kC = new KotlinCrasher(null);
    }

    private void stackOverflow() {
        stackOverflow();
    }

    private void freeze() {
        int a=0;
        while(true) {
            a++;
        }
    }

    private void runTime() {
        throw new RuntimeException("This is a crash");
    }

    private void nullPointer() {
        Integer nullInt = null;
        nullInt = nullInt + 1;
    }

    private void crashOutOfBound() {
        String[] stringBuster = new String[2];
        String bust = stringBuster[3];
    }

    private void crashOutOfMemory() {
        int iteratorValue = 1000;
        for (int outerIterator = 1; outerIterator < 20; outerIterator++) {
            int loop1 = 2;
            int[] memoryFillIntVar = new int[iteratorValue];
            // feel memoryFillIntVar array in loop..
            do {
                memoryFillIntVar[loop1] = 0;
                loop1--;
            } while (loop1 > 0);
            iteratorValue = iteratorValue * 5;
        }
    }
}
