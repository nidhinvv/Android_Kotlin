package com.jeebley.task.adapter


import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.jeebley.task.extensions.inflate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*


class RxAdapter<DataType, ViewHolder: SimpleViewHolder<DataType>>(dataSet: List<DataType>, @param:LayoutRes private val mItem_layout: Int = 0) : RecyclerView.Adapter<ViewHolder>() {
    private var dataSet: List<DataType>
    private val mPublishSubject: PublishSubject<ViewHolder>

    private var viewCreator: ((ViewGroup, Int) -> ViewHolder)? = null
    private var viewTypeGetter: ((Int) -> Int)? = null
    private var itemCountGetter: (() -> Int)? = null

    init {
        this.dataSet = dataSet
        mPublishSubject = PublishSubject.create()
    }

    fun asObservable(): Observable<ViewHolder> {
        return mPublishSubject
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var holder: ViewHolder =
                                if(null != viewCreator){
                                    viewCreator?.invoke(parent, viewType)!!
                                }
                                else{
                                    val view = parent.inflate(mItem_layout)
                                    SimpleViewHolder<DataType>(view, viewType) as ViewHolder
                                }

        checkNotNull(holder)

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item = if(dataSet.size > position) dataSet[position] else dataSet[dataSet.size - 1]
        holder.pos = position
        holder.adapter = this

        mPublishSubject.onNext(holder)
    }

    override fun getItemCount(): Int {
        return if(null != itemCountGetter) itemCountGetter?.invoke()!! else dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(null != viewTypeGetter) viewTypeGetter?.invoke(position)!! else super.getItemViewType(position)
    }

    fun viewHolderCreator(creator: ((ViewGroup, Int) -> ViewHolder)): RxAdapter<DataType, ViewHolder> {
        viewCreator = creator

        return this
    }

    fun viewTypeGetter(getter: ((Int) -> Int)): RxAdapter<DataType, ViewHolder> {
        viewTypeGetter = getter

        return this
    }

    fun itemCountGetter(getter: () -> Int): RxAdapter<DataType, ViewHolder> {
        itemCountGetter = getter

        return this
    }

    fun updateDataSet(dataSet: List<DataType>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }

    fun update(){
        updateDataSet(dataSet)
    }

    fun bindRecyclerView(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager): RxAdapter<DataType, ViewHolder> {
        recyclerView.adapter = this
        recyclerView.layoutManager = layoutManager

        return this
    }

    // Transformation methods
    fun map(mapper: (DataType) -> DataType): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).map(mapper).toList().blockingGet()
        return this
    }

    fun filter(predicate: (DataType) -> Boolean): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).filter(predicate).toList().blockingGet()
        return this
    }

    fun last(): RxAdapter<DataType, ViewHolder> {
        dataSet = listOf(Observable.fromIterable(dataSet).blockingLast())
        return this
    }

    fun first(): RxAdapter<DataType, ViewHolder> {
        dataSet = listOf(Observable.fromIterable(dataSet).blockingFirst())
        return this
    }

    fun lastOrDefault(defaultValue: DataType): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet)
                .takeLast(1)
                .defaultIfEmpty(defaultValue)
                .toList()
                .blockingGet()
        return this
    }

    fun limit(count: Int): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).take(count.toLong()).toList().blockingGet()
        return this
    }

    fun repeat(count: Long): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).repeat(count).toList().blockingGet()
        return this
    }

    fun empty(): RxAdapter<DataType, ViewHolder> {
        dataSet = Collections.emptyList<DataType>()
        return this
    }

    fun concatMap(func: (DataType) -> Observable<out DataType>): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).concatMap(func).toList().blockingGet()
        return this
    }

    fun concatWith(observable: Observable<out DataType>): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).concatWith(observable).toList().blockingGet()
        return this
    }

    fun distinct(): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).distinct().toList().blockingGet()
        return this
    }

    fun elementAt(index: Long): RxAdapter<DataType, ViewHolder> {
        dataSet = listOf(Observable.fromIterable(dataSet).elementAt(index).blockingGet())
        return this
    }

    fun elementAtOrDefault(index: Long, defaultValue: DataType): RxAdapter<DataType, ViewHolder> {
        dataSet = listOf(Observable.fromIterable(dataSet).elementAt(index, defaultValue)
                .blockingGet())
        return this
    }

    fun first(defaultItem: DataType): RxAdapter<DataType, ViewHolder> {
        dataSet = listOf(Observable.fromIterable(dataSet).first(defaultItem).blockingGet())
        return this
    }

    fun flatMap(func: (DataType) -> Observable<out DataType>): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).flatMap(func).toList().blockingGet()
        return this
    }

    fun reduce(initialValue: DataType, reducer: (DataType, DataType) -> DataType): RxAdapter<DataType, ViewHolder> {
        dataSet = listOf(Observable.fromIterable(dataSet).reduce(initialValue, reducer).blockingGet())
        return this
    }

    fun take(count: Long): RxAdapter<DataType, ViewHolder> {
        dataSet = Observable.fromIterable(dataSet).take(count).toList().blockingGet()
        return this
    }
}