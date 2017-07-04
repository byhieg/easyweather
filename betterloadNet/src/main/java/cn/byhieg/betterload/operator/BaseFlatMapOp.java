package cn.byhieg.betterload.operator;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Function;

/**
 * Created by byhieg on 17/5/12.
 * Contact with byhieg@gmail.com
 */

public interface BaseFlatMapOp<T,R> extends Func1<T, Observable<R>>{

}
