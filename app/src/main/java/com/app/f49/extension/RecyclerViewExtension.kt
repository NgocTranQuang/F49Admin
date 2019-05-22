package extension

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.app.f49.R
import com.app.f49.decoration.DividerItemDecoration

//
//
//
//fun RecyclerView.addCustomItemDecoration() {
//    val SimpleDividerItemDecoration = SimpleDividerItemDecoration(context)
//    addItemDecoration(SimpleDividerItemDecoration)
//}
//
fun RecyclerView.init(int: Int = LinearLayoutManager.VERTICAL, space: Int = R.dimen.activity_horizontal_margin) {

    layoutManager = LinearLayoutManager(context, int, false)

    var decoration = DividerItemDecoration(
        context.resources.getDimensionPixelSize(space),
        int
    )
    addItemDecoration(decoration)

}
//fun RecyclerView.binListComment(list: MutableList<CommentDTO>?, clickReplyOfReply : ((MutableList<CommentDTO>, String) -> Unit)?) {
//    if (list != null) {
//        layoutManager = LinearLayoutManager(context)
//        var adapter = TopCommentAdapter(list,clickReplyOfReply,this)
//        if(itemDecorationCount ==0) {
//            var decoration = DividerItemDecoration(
//                resources.getDimensionPixelSize(R.dimen.height_line_size),
//                DividerItemDecoration.VERTICAL
//            )
//            addItemDecoration(decoration)
//        }
//        this.adapter = adapter
//    } else {
//        visibility = View.GONE
//    }
//}