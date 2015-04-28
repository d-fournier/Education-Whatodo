# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('whatodo', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='city',
            name='latitude',
            field=models.DecimalField(decimal_places=7, max_digits=10, default=0),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='city',
            name='longitude',
            field=models.DecimalField(decimal_places=7, max_digits=10, default=0),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='event',
            name='latitude',
            field=models.DecimalField(decimal_places=7, max_digits=10, default=0),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='event',
            name='longitude',
            field=models.DecimalField(decimal_places=7, max_digits=10, default=0),
            preserve_default=False,
        ),
    ]
