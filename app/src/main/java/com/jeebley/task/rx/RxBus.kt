package com.jeebley.task.rx

import rx.Observable
import rx.Subscription
import rx.subjects.PublishSubject
import rx.subjects.SerializedSubject
import rx.subscriptions.CompositeSubscription
import java.util.*

/**
 * Simple Rx Event RxBus
 */
object RxBus {
    private val TAG = javaClass.simpleName

    /**
     * Used to hold all subscriptions for RxBus events and unsubscribe properly when needed.
     */
    private val subscriptionsMap: HashMap<Any, CompositeSubscription?> by lazy {
        HashMap<Any, CompositeSubscription?>()
    }

    /**
     * Avoid using this property directly, exposed only because it's used in inline fun
     */
    val bus = SerializedSubject(PublishSubject.create<Any>())

    /**
     * Sends an event to RxBus. Can be called from any thread
     */
    fun send(event: Any) {
        bus.onNext(event)
    }

    /**
     * Subscribes for events of certain type T. Can be called from any thread
     */
    inline fun <reified T : Any> observe(): Observable<T> {
        return bus.ofType(T::class.java)
    }

    /**
     * Unregisters subscriber from RxBus events.
     * Calls unsubscribe method of your subscriptions
     * @param subscriber subscriber to unregister
     */
    fun unregister(subscriber: Any) {
        val compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription != null) {
            compositeSubscription.clear()
            subscriptionsMap.remove(subscriber)
        }
    }

    internal fun register(subscriber: Any, subscription: Subscription) {
        var compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription.add(subscription)
        subscriptionsMap[subscriber] = compositeSubscription
    }
}

/**
 * Registers the subscription to correctly unregister it later to avoid memory leaks
 * @param subscriber subscriber object that owns this subscription
 */
fun Subscription.registerInBus(subscriber: Any) {
    RxBus.register(subscriber, this)
}