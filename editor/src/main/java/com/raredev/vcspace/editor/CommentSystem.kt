/*
 * This file is part of Visual Code Space.
 *
 * Visual Code Space is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 *
 * Visual Code Space is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Visual Code Space.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.raredev.vcspace.editor

import io.github.rosemoe.sora.text.Content
import io.github.rosemoe.sora.text.batchEdit
import org.eclipse.tm4e.languageconfiguration.model.CommentRule

fun addSingleComment(commentRule: CommentRule?, text: Content) {
  commentRule ?: return
  val comment = commentRule.lineComment ?: return

  text.insert(text.cursor.leftLine, 0, comment)
}

fun addBlockComment(commentRule: CommentRule?, text: Content) {
  commentRule ?: return
  val blockComment = commentRule.blockComment
  val openPrefix = blockComment?.open ?: return
  val closePrefix = blockComment?.close ?: return

  val cursor = text.cursor
  if (cursor.isSelected) {
    text.batchEdit {
      // Insert multi-line comment at the beginning of the start line
      it.insert(cursor.leftLine, cursor.leftColumn, openPrefix)

      // Insert multi-line comment end at the end of the end line
      it.insert(cursor.rightLine, cursor.rightColumn, closePrefix)
    }
  }
}
