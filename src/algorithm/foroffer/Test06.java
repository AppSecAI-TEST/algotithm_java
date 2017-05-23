package algorithm.foroffer;

import java.util.Arrays;

/**
 * Created by liyazhou on 2017/5/23.
 * 面试题6 重建二叉树
 * 题目：
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
 * 假设输入的前序遍历和中序遍历的结果都不含重复的数字。例如，
 * 输入前序遍历序列 {1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
 * 则重建出二叉树并输出它的头结点。
 */

class BinaryTreeNode{
    private int value;  // id of BinaryTreeNode, unique
    private BinaryTreeNode leftChildNode;
    private BinaryTreeNode rightChildNode;

    public BinaryTreeNode(){}
    public BinaryTreeNode(int value){ this.value = value; }
    public BinaryTreeNode(int value, BinaryTreeNode leftChildNode, BinaryTreeNode rightChildNode){
        this.value = value;
        this.leftChildNode = leftChildNode;
        this.rightChildNode = rightChildNode;
    }

    public int getValue(){ return this.value; }
    public BinaryTreeNode getLeftChildNode(){ return this.leftChildNode; }
    public BinaryTreeNode getRightChildNode(){ return this.rightChildNode; }

    public void setValue(int value){ this.value = value; }
    public void setLeftChildNode(BinaryTreeNode leftChildNode){ this.leftChildNode = leftChildNode; }
    public void setRightChildNode(BinaryTreeNode rightChildNode){ this.rightChildNode = rightChildNode; }
}


public class Test06 {

    public BinaryTreeNode constructBinTree(int[] preorder, int[] inorder){
        // 递归终止条件
        if(preorder == null || preorder.length == 0) return null;

        //从先序遍历结果中取出根结点
        int rootId = preorder[0];
        BinaryTreeNode rootNode = new BinaryTreeNode(rootId);

        // 查找该根结点在中序遍历结果中的位置（索引）
        int idxOfRoot = 0;
        for(int i = 0; i < inorder.length; i++){
            if(inorder[i] == rootId){
                idxOfRoot = i;
                break;
            }
        }

        int leftChildNum = idxOfRoot;
        int rightChildNum = inorder.length - idxOfRoot - 1;

        // 左孩子构成的子树
        if (leftChildNum > 0) {
            int[] leftPreorder = new int[leftChildNum];
            int[] leftInorder = new int[leftChildNum];
            for (int i = 0; i < leftChildNum; i++){
                leftPreorder[i] = preorder[1+i];  // 取出先序遍历结果中根结点的左孩子结点
                leftInorder[i] = inorder[i];  // 取出中序遍历结果中根结点的左孩子结点
            }
            if (!compareArrays(leftPreorder, leftInorder)) {
                throw new RuntimeException(rootId + "的左孩子的先序遍历和中序遍历结果不匹配");
            }
            System.out.println(String.format("%d 的左子树的先序遍历结果：%s", rootId, Arrays.toString(leftPreorder)));
            System.out.println(String.format("%d 的左子树的中序遍历结果：%s", rootId, Arrays.toString(leftInorder)));
            BinaryTreeNode leftChildNode = constructBinTree(leftPreorder, leftInorder);
            rootNode.setLeftChildNode(leftChildNode);
        }

        // 右孩子构成的子树
        if (rightChildNum > 0){
            int[] rightPreorder = new int[rightChildNum];
            int[] rightInorder = new int[rightChildNum];
            for(int k = 0; k < rightChildNum; k++){
                rightPreorder[k] = preorder[1+leftChildNum+k];  // 取出先序遍历结果中根结点的右孩子结点
                rightInorder[k] = inorder[1+leftChildNum+k];  // 取出中序遍历结果中根结点的右孩子结点
            }
            if (!compareArrays(rightPreorder, rightInorder)) {
                throw new RuntimeException(rootId + "的右孩子的先序遍历和中序遍历结果不匹配");
            }
            System.out.println(String.format("%d 的右子树的先序遍历结果：%s", rootId, Arrays.toString(rightPreorder)));
            System.out.println(String.format("%d 的右子树的中序遍历结果：%s", rootId, Arrays.toString(rightInorder)));
            BinaryTreeNode rightChildNode = constructBinTree(rightPreorder, rightInorder);
            rootNode.setRightChildNode(rightChildNode);
        }

        return rootNode;
    }

    private boolean compareArrays(int[] arr1, int[] arr2){
        if (arr1 == null || arr2 == null) return false;
        if (arr1.length != arr2.length) return false;
        int[] copyOfArr1 = Arrays.copyOf(arr1, arr1.length);
        int[] copyOfArr2 = Arrays.copyOf(arr2, arr2.length);
        Arrays.sort(copyOfArr1);
        Arrays.sort(copyOfArr2);
        // System.out.println(Arrays.toString(arr1));
        // System.out.println(Arrays.toString(copyOfArr1));
        // System.out.println(arr1 == copyOfArr1);
        // Arrays.deepEquals(arr1, arr2);
        boolean equals = true;
        for (int i = 0; i < copyOfArr1.length; i++){
            if (copyOfArr1[i] != copyOfArr2[i]){
                equals = false;
                break;
            }
        }
        return equals;
    }



    public BinaryTreeNode constructBinTree2(int[] preorder, int[] inorder){
        // 判断输入数据
        if(preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) return null;
        return constructBinTree(preorder, 0, preorder.length, inorder, 0, preorder.length);
    }

    // todo, 确定边界是关键点
    private BinaryTreeNode constructBinTree(int[] preorder, int ps, int pe, int[] inorder, int is, int ie) {
        // 递归终止条件，当开始位置大于结束位置时，则没有要处理的数据
        if (ps > pe) return null;

        // 从前序遍历序列中取出根结点
        int rootId = preorder[0];
        BinaryTreeNode root = new BinaryTreeNode(rootId);

        // 根结点在中序遍历序列中的位置
        int idxOfRoot = -1;
        for (int i = is; i <= ie; i++){
            if(inorder[i] == rootId){
                idxOfRoot = i;
                break;
            }
        }

        if (idxOfRoot == -1) throw new RuntimeException("先序遍历序列和中序遍历序列不匹配.");

        // 递归构建当前根结点的左子树，左子树的结点个数是 idxOfRoot-is
        // 先序遍历序列中，当前根结点的左子树的范围是 [ps+1, ps+(idxOfRoot-is))
        // 中序遍历序列中，当前根结点的左子树的范围是 [is, idxOfRoot+1)
        BinaryTreeNode leftChildNode = constructBinTree(preorder, ps+1, ps+idxOfRoot-is+1, inorder, is, idxOfRoot);
        root.setLeftChildNode(leftChildNode);

        // 递归构建当前根结点的右子树，右子树的结点个数是 ie-idxOfRoot
        // 先序遍历序列中，当前结点的右子树的范围是 [pe-(ie-idxOfRoot)-1, pe)
        // 中序遍历序列中，当前结点的右子树的范围是 [idxOfRoot+1, ie)
        BinaryTreeNode rightChildNode = constructBinTree(preorder, pe-ie+idxOfRoot-1 , pe+1, inorder, idxOfRoot+1, ie);
        root.setRightChildNode(rightChildNode);

        return root;
    }



    public void previsit(BinaryTreeNode root){
        if (root == null) return;
        System.out.print(String.format("%s \t", root.getValue()));
        previsit(root.getLeftChildNode());
        previsit(root.getRightChildNode());
    }

    public void invisit(BinaryTreeNode root){
        if (root == null) return;
        invisit(root.getLeftChildNode());
        System.out.print(String.format("%s \t", root.getValue()));
        invisit(root.getRightChildNode());
    }

    public void postvisit(BinaryTreeNode root){
        if (root == null) return;
        postvisit(root.getLeftChildNode());
        postvisit(root.getRightChildNode());
        System.out.print(String.format("%s \t", root.getValue()));
    }

    public void visit(BinaryTreeNode root){
        System.out.print("先序遍历结果：");
        previsit(root);
        System.out.println();
        System.out.print("中序遍历结果：");
        invisit(root);
        System.out.println();
        System.out.print("后序遍历结果：");
        postvisit(root);
        System.out.println("\n---------------------");
    }

    /**
     * 普通二叉树
     */
    public void test1(){
        int[] preorder = {1,2,4,7,3,5,6,8};
        int[] inorder = {4,7,2,1,5,3,8,6};
        BinaryTreeNode root = constructBinTree2(preorder, inorder);
        visit(root);
    }

    /**
     * 完全二叉树
     */
    public void test5(){
        int[] preorder = {1, 2, 4, 5, 3, 6, 7};
        int[] inorder = {4, 2, 5, 1, 6, 3, 7};
        BinaryTreeNode root = constructBinTree2(preorder, inorder);
        visit(root);
    }

    /**
     * 所有结点都没有右孩子
     */
    public void test2(){
        int[] preorder = {1, 2, 3, 4, 5};
        int[] inorder = {5, 4, 3, 2, 1};
        BinaryTreeNode root = constructBinTree2(preorder, inorder);
        visit(root);
    }

    /**
     * 所有结点都没有左孩子
     */
    public void test3(){
        int[] preorder = {1, 2, 3, 4, 5};
        int[] inorder = {1, 2, 3, 4, 5};
        BinaryTreeNode root = constructBinTree2(preorder, inorder);
        visit(root);
    }


    /**
     * 只有一个结点
     */
    public void test4(){
        int[] preorder = {1};
        int[] inorder = {1};
        BinaryTreeNode root = constructBinTree2(preorder, inorder);
        visit(root);
    }

    /**
     * 空指针
     */
    public void test6(){
        BinaryTreeNode root = constructBinTree2(null, null);
        visit(root);
    }

    /**
     * 前序遍历和中序遍历不匹配
     */
    public void test7(){
        int[] preorder = {1,3,2,4,7,5,6,8};
        int[] inorder = {4,7,2,1,5,3,8,6};
        BinaryTreeNode root = constructBinTree2(preorder, inorder);
        visit(root);
    }


    public static void main(String[] args){
        Test06 test = new Test06();
        test.test1();
        test.test2();
        test.test3();
        test.test4();
        test.test5();
        test.test6();
        test.test7();
    }

}
